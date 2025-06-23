package com.example.sample.service;
import com.example.sample.dao.UserRepo;
import com.example.sample.dto.UserRequestDto;
import com.example.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User saveUser(UserRequestDto userDto) {

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user.setFullName(userDto.getFullName());
        user.setAcceptanceStatus(userDto.getAcceptanceStatus());
        return repo.save(user) ;

    }
}
