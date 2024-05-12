package com.luanvan.luanvan.subjectclassservice.service;



import com.luanvan.luanvan.groupService.repository.StudentRepository;
import com.luanvan.luanvan.subjectclassservice.model.SubjectClass;
import com.luanvan.luanvan.subjectclassservice.repository.SubjectClassReponsitory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class SubjectClassService  {
    SubjectClassReponsitory subjectClassReponsitory;

    StudentRepository studentRepository;



    public Optional<SubjectClass> findById(Integer integer) {
        return subjectClassReponsitory.findById(integer);
    }

    public List<SubjectClass> findAll() {
        return subjectClassReponsitory.findAll();
    }


    public SubjectClass save(SubjectClass sc) {
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        sc.setCreatedAt(timestamp);
        return subjectClassReponsitory.save(sc);
    }

    public void deleteById(Integer id) {
        subjectClassReponsitory.deleteById(id);
    }


    public SubjectClass updateSc(Integer id, SubjectClass subjectClass){
        SubjectClass sc = subjectClassReponsitory.findById(id).orElseThrow(() -> new EntityNotFoundException("Khong co lop hoc voi id:"+id));
        sc.setSubjectName(subjectClass.getSubjectName());
        sc.setCreatedBy(subjectClass.getCreatedBy());
        sc.setCreatedAt(subjectClass.getCreatedAt());
        sc.setSchoolYear(subjectClass.getSchoolYear());
        sc.setNumberOfGroup(subjectClass.getNumberOfGroup());
        sc.setMemberPerGroup(subjectClass.getMemberPerGroup());
        sc.setGroupRegisterMethod(subjectClass.getGroupRegisterMethod());
        subjectClassReponsitory.save(sc);
        return sc;
    }

    public List<SubjectClass> getClassByCreatedBy(Integer userId) {
        return subjectClassReponsitory.findByCreatedBy(userId);
    }

    public List<SubjectClass> getSubjectClassesByStudentId(int studentId) {
        return subjectClassReponsitory.findByStudentId(studentId);
    }
}
