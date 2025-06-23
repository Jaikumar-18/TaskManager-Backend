package com.example.sample.mapper;

import com.example.sample.dto.UserDto;
import com.example.sample.dto.UserRequestDto;
import com.example.sample.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setAcceptanceStatus(dto.getAcceptanceStatus());
        return user;
    }

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setAcceptanceStatus(user.getAcceptanceStatus());
        return dto;
    }
}
