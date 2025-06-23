package com.example.sample.service;

import com.example.sample.dao.TaskRepo;
import com.example.sample.dao.UserRepo;
import com.example.sample.dto.TaskDto;
import com.example.sample.mapper.TaskMapper;
import com.example.sample.model.Task;
import com.example.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.sample.exception.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserRepo userRepo;

    public List<TaskDto> convertToTaskDtoList(List<Task> tasks) {
        return tasks.stream().map(this::convertToTaskDto).collect(Collectors.toList());
    }

    private TaskDto convertToTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        if(task.getAssignedTo() != null){
            dto.setAssignedToId(task.getAssignedTo().getUserId());
        }
        dto.setCreatedById(task.getCreatedBy().getUserId());


        return dto;
    }

    public List<TaskDto> getAllTasks(Optional<String> status) {
        List<Task> tasks;
        if (status.isPresent()) {
            tasks = taskRepo.findByStatusAndAssignedToIsNotNullOrderByDueDate(status.get());
        } else {
            tasks = taskRepo.findByAssignedToIsNotNullOrderByDueDate();
        }
        return convertToTaskDtoList(tasks);
    }



    public List<TaskDto> getMyAssignTask(int id){

        List<Task> tasks = taskRepo.findTasksCreatedByUserWithAssignedNotNull(id);

        return convertToTaskDtoList(tasks);
    }

    public TaskDto createTask(TaskDto taskDto,String email){
        User creator = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not found"));

        Task task = taskMapper.toEntity(taskDto);

        task.setCreatedBy(creator);

        if(taskDto.getAssignedToId() != null){
            User assignedUser = userRepo.findById(taskDto.getAssignedToId())
                    .orElseThrow(()-> new UsernameNotFoundException("Assigned User Not Found"));

            task.setAssignedTo(assignedUser);
        }
        Task saveTask = taskRepo.save(task);
        return taskMapper.toDto(saveTask);
    }

    public TaskDto getTaskById(int taskId){

        Task task = taskRepo.findById(taskId)
                .orElseThrow(()-> new ResourceNotFoundException("Task Not found with Id :"
                + taskId));
        return taskMapper.toDto(task);
    }

    public TaskDto updateTask(int taskId,TaskDto updateTask){

        Task task = taskRepo.findById(taskId)
                .orElseThrow(()-> new ResourceNotFoundException("Task Not found with Id :"
                        + taskId));

        task.setTitle(updateTask.getTitle());
        task.setDescription(updateTask.getDescription());
        task.setStatus(updateTask.getStatus());
        task.setDueDate(updateTask.getDueDate());
        task.setPriority(updateTask.getPriority());
        if(updateTask.getAssignedToId() != null){
            User assignedUser = userRepo.findById(updateTask.getAssignedToId())
                    .orElseThrow(()-> new UsernameNotFoundException("Assigned User Not Found"));
            task.setAssignedTo(assignedUser);
        }

        Task saved = taskRepo.save(task);
        return taskMapper.toDto(saved);

    }

    public void deleteTask(int taskId){
        Task task = taskRepo.findById(taskId)
                .orElseThrow(()-> new ResourceNotFoundException("Task Not found with Id :"
                        + taskId));
        taskRepo.delete(task);
    }


    public Integer getAllTasksCount() {
        return Math.toIntExact(taskRepo.countByAssignedToIsNotNull());
    }


    public long getAssignedTaskCount(Integer userId) {

        return taskRepo.countTaskByAssignedToUserId(userId);
    }

    public long getCreatedTaskCount(Integer userId) {

        return taskRepo.countTaskByCreatedByUserId(userId);
    }

    public long getCompletedTaskCount(){

        return taskRepo.countCompletedTasks();
    }

    public Map<String, Long> getTaskStatusCountsWhereAssigned() {
        List<Object[]> results = taskRepo.countTasksByStatusWhereAssigned();
        Map<String, Long> statusCounts = new HashMap<>();
        for (Object[] result : results) {
            String status = (String) result[0].toString();
            Long count = (Long) result[1];
            statusCounts.put(status, count);
        }
        return statusCounts;
    }

    public long getMyCompletedTaskCount(Integer userId) {

        return taskRepo.countCompletedTasksByUserId(userId);
    }

    public long getMyPendingTaskCount(Integer userId) {
        return taskRepo.countPendingTasksByUserId(userId);
    }

    public List<TaskDto> getMyAssignedTask(Integer userId){
        List<Task> task = taskRepo.findByMyAssignedTask(userId);
        return convertToTaskDtoList(task);
    }
    public List<TaskDto> getMyCreatedTask(Integer userId){
        List<Task> task = taskRepo.findByMyCreatedTask(userId);
        return convertToTaskDtoList(task);
    }

}
