package com.example.sample.mapper;
import com.example.sample.dto.TaskDto;
import com.example.sample.model.Task;
import com.example.sample.model.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskDto taskDto){
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());
        task.setDueDate(taskDto.getDueDate());

        return task;
    }

    public TaskDto toDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(task.getTaskId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setPriority(task.getPriority());
        taskDto.setStatus(task.getStatus());
        taskDto.setDueDate(task.getDueDate());
        if(task.getAssignedTo()!=null){
            taskDto.setAssignedToId(task.getAssignedTo().getUserId());
        }
        if(task.getCreatedBy()!=null){
            taskDto.setCreatedById(task.getCreatedBy().getUserId());
        }
        return taskDto;
    }
}
