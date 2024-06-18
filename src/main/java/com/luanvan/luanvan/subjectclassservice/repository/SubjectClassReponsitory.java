package com.luanvan.luanvan.subjectclassservice.repository;

import com.luanvan.luanvan.subjectclassservice.model.SubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectClassReponsitory extends JpaRepository<SubjectClass,Integer> {
    List<SubjectClass> findByCreatedBy(Integer userId);
    @Query("SELECT sc FROM SubjectClass sc JOIN StudentList sl ON sc.subjectClassId = sl.classId WHERE sl.studentId = ?1")
    List<SubjectClass> findByStudentId(int studentId);
    Optional<SubjectClass> findByInviteCode(String inviteCode);


}
