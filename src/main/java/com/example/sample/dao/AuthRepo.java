package com.example.sample.dao;

import com.example.sample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepo extends JpaRepository<User,Integer> {

    User findByEmail(String email);
}
