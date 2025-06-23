package com.example.sample.controller;
import com.example.sample.dto.TaskDto;
import com.example.sample.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("tasks")
    public ResponseEntity<List<TaskDto>> getAllTask(@RequestParam Optional<String> status){
        List<TaskDto> tasks = taskService.getAllTasks(status);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto, Principal principal){
        TaskDto createTask = taskService.createTask(taskDto,principal.getName());
        return new ResponseEntity<>(createTask, HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id:[0-9]+}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable int id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable int id,@RequestBody TaskDto taskDto){
        return ResponseEntity.ok(taskService.updateTask(id,taskDto));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-tasks/{userId}")
    public ResponseEntity<Map<String,List<TaskDto>>> getMyTasks(@PathVariable Integer userId){
        List<TaskDto> myAssignedTask = taskService.getMyAssignedTask(userId);
        List<TaskDto> myCreatedTask = taskService.getMyCreatedTask(userId);
        Map<String,List<TaskDto>> response = new HashMap<>();
        response.put("myAssignedTask",myAssignedTask);
        response.put("myCreatedTask",myCreatedTask);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-assign-task/{id}")
    public ResponseEntity<List<TaskDto>> getMyAssignTasks(@PathVariable int id){
        return ResponseEntity.ok(taskService.getMyAssignTask(id));
    }

    @GetMapping("my-tasks/count/{userId}")
    public ResponseEntity<Map<String, Long>> getTaskCounts(@PathVariable Integer userId) {
        long assignedCount = taskService.getAssignedTaskCount(userId);
        long createdCount = taskService.getCreatedTaskCount(userId);
        long completedCount = taskService.getMyCompletedTaskCount(userId);
        long pendingCount = taskService.getMyPendingTaskCount(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("assignedCount", assignedCount);
        response.put("createdCount", createdCount);
        response.put("completedCount", completedCount);
        response.put("pendingCount", pendingCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks/count")
    public ResponseEntity<Integer> getAllTaskCount(){
        return ResponseEntity.ok(taskService.getAllTasksCount());
    }

    @GetMapping("/tasks/completed/count")
    public ResponseEntity<Long> getCompletedTaskCount(){
        return ResponseEntity.ok(taskService.getCompletedTaskCount());
    }


    @GetMapping("/tasks/status-count")
    public ResponseEntity<Map<String,Long>> getAssignedTaskStatus(){
        return ResponseEntity.ok(taskService.getTaskStatusCountsWhereAssigned());
    }
}