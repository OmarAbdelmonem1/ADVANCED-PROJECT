package com.adv.adv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.adv.adv.model.*;

public interface userRepository extends JpaRepository<User,Long> {
  
        Optional<User> findByEmail(String email);
        Optional<User> findByUsername(String username);
        Boolean existsByEmail(String email);
        Boolean existsByUsername(String username);
    }
    
