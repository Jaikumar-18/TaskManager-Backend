package com.example.sample.dao;

import com.example.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("Select u From User u where u.acceptanceStatus ='ACCEPTED'")
    List<User> findAllApprovedUser();

    @Query("Select u From User u where u.acceptanceStatus ='REQUEST'")
    List<User> findAllNonApprovedUser();


}
