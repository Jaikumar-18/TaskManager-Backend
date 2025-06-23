package com.example.sample.dto;

import lombok.Data;

@Data
public class UserRequestDto {

    private String fullName;
    private String email;
    private String password;
    private String role;
    private String acceptanceStatus;
}
