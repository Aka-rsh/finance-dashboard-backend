package com.finance.dashboard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance.dashboard.Repository.UserRepository;
import com.finance.dashboard.entities.Roles;
import com.finance.dashboard.entities.User;
import com.finance.dashboard.entities.UserStatus;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

 // 1. REGISTER a new user
    public User registerUser(User user) {
        // Step A: Check if the email already exists
        boolean exists = userRepository.existsByUserEmail(user.getUserEmail());
        
        if (exists) {
            throw new RuntimeException("Email is already registered!");
        }

        // Step B: ENCRYPT THE PASSWORD (VERY IMPORTANT)
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);

        // Step C: Set default values
        user.setStatus(UserStatus.ACTIVE);
        
        // Step D: Save to database
        return userRepository.save(user);
    }

    // 2. FIND a user by email (Useful for Login)
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByUserEmail(email);
        
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null; // Or throw an exception
        }
    }

    // 3. MANAGE ROLES (Admin can change a user's role)
    public User updateUserRole(Long userId, Roles role) {
        Optional<User> optionalUser = userRepository.findById(userId);
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // FIXED: Use the 'role' passed in the argument, not hardcoded 'Roles.ADMIN'
            user.setRole(role); 
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    // 4. TOGGLE STATUS (Active/Inactive)
    public User setUserStatus(Long userId, boolean status) {
        Optional<User> optionalUser = userRepository.findById(userId);
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // FIXED: Map the boolean 'status' to your UserStatus enum
            user.setStatus(status ? UserStatus.ACTIVE : UserStatus.INACTIVE);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // 5. GET ALL USERS (For Admin management)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}