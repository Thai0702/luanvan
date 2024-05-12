package com.luanvan.luanvan.securityService.repository;

import com.luanvan.luanvan.securityService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User>findByUsername(String username);
    Boolean existsUsersByUsername(String username);
}
