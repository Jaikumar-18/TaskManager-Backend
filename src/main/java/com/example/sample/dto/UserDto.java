package com.example.sample.dto;

import lombok.Data;

@Data
public class UserDto {
    private int userId;
    private String fullName;
    private String email;
    private String role;
    private String acceptanceStatus;
}
