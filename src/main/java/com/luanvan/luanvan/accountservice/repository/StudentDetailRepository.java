package com.luanvan.luanvan.accountservice.repository;

import com.luanvan.luanvan.accountservice.model.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentDetailRepository extends JpaRepository<StudentDetail,Integer> {
    public StudentDetail findStudentDetailByUserId(Integer userId);
    Optional<StudentDetail> findByStudentId(String studentId);
}
