package com.finance.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.finance.dashboard.entities.User;
import com.finance.dashboard.entities.Roles;
import com.finance.dashboard.service.UserService;
import com.finance.dashboard.dto.response.ResponseStructureDto;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. REGISTER a new user
    @PostMapping("/register")
    public ResponseEntity<ResponseStructureDto<User>> register(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        
        ResponseStructureDto<User> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User registered successfully");
        response.setData(savedUser);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. CHANGE USER ROLE (Admin only logic will be in Security later)
    @PutMapping("/{id}/role")
    public ResponseEntity<ResponseStructureDto<User>> updateRole(
            @PathVariable Long id, 
            @RequestParam Roles role) {
        
        User updatedUser = userService.updateUserRole(id, role);
        
        ResponseStructureDto<User> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Role updated to " + role);
        response.setData(updatedUser);
        
        return ResponseEntity.ok(response);
    }

    // 3. GET ALL USERS
    @GetMapping("/all")
    public ResponseEntity<ResponseStructureDto<List<User>>> getAll() {
        List<User> users = userService.getAllUsers();
        
        ResponseStructureDto<List<User>> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users fetched successfully");
        response.setData(users);
        
        return ResponseEntity.ok(response);
    }
}