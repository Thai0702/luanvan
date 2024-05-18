package com.luanvan.luanvan.securityService.repository;

import com.luanvan.luanvan.securityService.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User>findByUsername(String username);
    Boolean existsUsersByUsername(String username);
}
