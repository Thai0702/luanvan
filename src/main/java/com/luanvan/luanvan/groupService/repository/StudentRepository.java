package com.luanvan.luanvan.groupService.repository;

import com.luanvan.luanvan.accountservice.model.Account;
import com.luanvan.luanvan.groupService.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    public List<Student>getStudentsByClassId(Integer classId);
    public List<Student>getStudentsByStudentId(Integer studentId);
    List<Student> findByStudentId(Integer studentId);
    List<Student> findByStudentIdAndClassId(Integer studentId,Integer classId);
    void deleteByClassIdAndStudentId(int classId, int studentId);
    boolean existsByClassIdAndStudentId(int classId, int studentId);


    @Query("SELECT studentId FROM Student WHERE studentId = :studentId")
    int findStudentId(@Param("studentId") int studentId);
    //boolean existsByGroupIdAndStudentId(int groupId, int studentId);
}
