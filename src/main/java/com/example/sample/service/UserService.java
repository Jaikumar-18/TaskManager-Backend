package com.example.sample.service;
import com.example.sample.dao.UserRepo;
import com.example.sample.dto.UserDto;
import com.example.sample.dto.UserRequestDto;
import com.example.sample.exception.ResourceNotFoundException;
import com.example.sample.mapper.UserMapper;
import com.example.sample.model.Task;
import com.example.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public UserDto createUser(UserRequestDto userRequestDto){
        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    public List<UserDto> getAllUsers(){
        List<User> users = userRepo.findAll();
        return users.stream().map(userMapper :: toDto).collect(Collectors.toList());
    }

    public UserDto findByEmail(String email){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not Found with Email "+email));

        return userMapper.toDto(user);
    }

    public void deleteUser(int id){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found with Ids "+ id));
        userRepo.delete(user);
    }

    public UserDto getUserById(int id){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found with Ids "+ id));
        return userMapper.toDto(user);
    }
     public UserDto getProfile(String email){
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found By email id "+ email));
        return userMapper.toDto(user);
    }

    public Integer getAllUsersCount(){
        return Math.toIntExact(userRepo.count());
    }

    public UserDto updateUser(int id,UserDto updateDto){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User Not found with Id :"
                        + id));

        user.setUserId(updateDto.getUserId());
        user.setFullName(updateDto.getFullName());
        user.setRole(updateDto.getRole());
        user.setEmail(updateDto.getEmail());
        User saved = userRepo.save(user);
        return userMapper.toDto(saved);

    }
    public String approval(int id){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User Not found with Id :"
                        + id));
        user.setAcceptanceStatus("ACCEPTED");
        userRepo.save(user);
        return "Accepted";
    }

}