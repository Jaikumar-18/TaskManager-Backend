package com.example.sample.controller;
import com.example.sample.dto.UserDto;
import com.example.sample.dto.UserRequestDto;
import com.example.sample.service.EmailService;
import com.example.sample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getProfile(Principal principal){
        return ResponseEntity.ok(userService.getProfile(principal.getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUser() throws AccessDeniedException {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequestDto userRequestDto,Principal principal) throws AccessDeniedException {
        UserDto currentUser = userService.findByEmail(principal.getName());
        if(!currentUser.getRole().equalsIgnoreCase("admin")){
            throw new AccessDeniedException("Only Admin can Access this Resource");
        }
        UserDto createdUser = userService.createUser(userRequestDto);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id,Principal principal) throws AccessDeniedException {
        UserDto currentUser = userService.findByEmail(principal.getName());
        if(!currentUser.getRole().equalsIgnoreCase("admin")){
            throw new AccessDeniedException("Only Admin can Access this Resource");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> getAllUsersCount(){
        return ResponseEntity.ok(userService.getAllUsersCount());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id,@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(id,userDto));
    }

    @PutMapping("/approval/{id}")
    public ResponseEntity<String> approvedById(@PathVariable int id){

        return ResponseEntity.ok(userService.approval(id));
    }


}