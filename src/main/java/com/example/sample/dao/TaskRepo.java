package com.example.sample.dao;

import com.example.sample.model.Task;
import com.example.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Integer> {

    List<Task> findByAssignedToIsNotNullOrderByDueDate();

    List<Task> findByStatusAndAssignedToIsNotNullOrderByDueDate(String status);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'Completed' AND t.assignedTo IS NOT NULL")
    long countCompletedTasks();
    @Query("SELECT t.status, COUNT(t) FROM Task t WHERE t.assignedTo IS NOT NULL GROUP BY t.status")
    List<Object[]> countTasksByStatusWhereAssigned();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo.id = :userId")
    long countTaskByAssignedToUserId(Integer userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.createdBy.id = :userId AND t.assignedTo IS NULL")
    long countTaskByCreatedByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo IS NOT NULL")
    long countByAssignedToIsNotNull();


    @Query("SELECT COUNT(t) FROM Task t WHERE (t.assignedTo.id = :userId OR t.createdBy.id = :userId) AND t.status = 'Completed'")
    long countCompletedTasksByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE (t.assignedTo.id = :userId OR t.createdBy.id = :userId) AND t.status = 'Pending'")
    long countPendingTasksByUserId(@Param("userId") Integer userId);

    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId")
    List<Task> findByMyAssignedTask(@Param("userId") Integer userId);

    @Query("SELECT t FROM Task t WHERE t.assignedTo IS NULL AND t.createdBy.id = :userId")
    List<Task> findByMyCreatedTask(@Param("userId") Integer userId);

    @Query("SELECT t FROM Task t WHERE t.createdBy.id = :userId AND t.assignedTo IS NOT NULL")
    List<Task> findTasksCreatedByUserWithAssignedNotNull(@Param("userId") Integer userId);

}
