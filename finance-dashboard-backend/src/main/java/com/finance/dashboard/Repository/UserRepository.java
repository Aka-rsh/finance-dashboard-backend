package com.finance.dashboard.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.finance.dashboard.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // 1. Find by the exact field name in your User entity (userEmail)
    Optional<User> findByUserEmail(String userEmail);
    
    // 2. Check if userEmail exists
    boolean existsByUserEmail(String userEmail);
    
    // 3. Find by userName (matches private String userName)
    Optional<User> findByUserName(String userName); 
}