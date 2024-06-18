package com.luanvan.luanvan.groupService.repository;

import com.luanvan.luanvan.groupService.model.StudentList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentList,Integer> {

    public List<StudentList>getStudentsByClassId(Integer classId);
    public List<StudentList>getStudentsByStudentId(Integer studentId);
    List<StudentList> findByStudentId(Integer studentId);
    List<StudentList> findByStudentIdAndClassId(Integer studentId, Integer classId);
    void deleteByClassIdAndStudentId(int classId, int studentId);
    boolean existsByClassIdAndStudentId(int classId, int studentId);
    @Query("SELECT studentId FROM StudentList WHERE studentId = :studentId")
    int findStudentId(@Param("studentId") int studentId);
    //boolean existsByGroupIdAndStudentId(int groupId, int studentId);
}
