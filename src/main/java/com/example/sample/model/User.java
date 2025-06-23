package com.example.sample.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String fullName;
    private String password;
    @Column(unique = true)
    private String email;
    private String role;
    private String acceptanceStatus;
    @OneToMany(mappedBy = "assignedTo",cascade = CascadeType.ALL)
    private List<Task> assignedTask;
    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL)
    private List<Task> createdTask;
    private LocalTime createdAt;
    private LocalTime updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalTime.now();
    }

}
