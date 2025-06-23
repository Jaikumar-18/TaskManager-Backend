package com.example.sample.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    private String title;
    @Column(length = 1000)
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private LocalTime createdAt;
    private LocalTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalTime.now();
    }

}
