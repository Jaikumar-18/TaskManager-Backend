package com.example.sample.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {

    private int taskId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private Integer assignedToId;
    private Integer createdById;

}
