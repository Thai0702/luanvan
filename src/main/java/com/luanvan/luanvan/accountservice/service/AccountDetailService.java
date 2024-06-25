package com.luanvan.luanvan.accountservice.service;

import com.luanvan.luanvan.accountservice.model.Account;
import com.luanvan.luanvan.accountservice.model.Role;
import com.luanvan.luanvan.accountservice.model.StudentDetail;
import com.luanvan.luanvan.accountservice.repository.AccountRepository;
import com.luanvan.luanvan.accountservice.repository.StudentDetailRepository;
import com.luanvan.luanvan.accountservice.wrapper.AdminAccountDetail;
import com.luanvan.luanvan.accountservice.wrapper.StudentAccountDetail;
import com.luanvan.luanvan.accountservice.wrapper.TeacherAccountDetail;
import com.luanvan.luanvan.groupService.model.Student;
import com.luanvan.luanvan.groupService.repository.StudentRepository;
import com.luanvan.luanvan.groupService.wrapper.StudentInfo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AccountDetailService {
    @Autowired
    AccountRepository accountRepository;
    StudentDetailRepository studentDetailRepository;
    StudentRepository studentRepository;

    public AccountDetailService(AccountRepository accountRepository, StudentDetailRepository studentDetailRepository, StudentRepository studentRepository) {
        this.accountRepository = accountRepository;
        this.studentDetailRepository = studentDetailRepository;
        this.studentRepository = studentRepository;
    }

    public StudentAccountDetail getStudentAccountDetail(int userId){
        Account account=accountRepository.getOne(userId);
        Optional<StudentDetail> findStudent=studentDetailRepository.findById(userId);
        if(findStudent.isPresent()){
            StudentDetail studentDetail=findStudent.get();
            StudentAccountDetail accountDetail = new StudentAccountDetail(account.getUserId(),
                    account.getFullName(),
                    studentDetail.getStudentId(),
                    studentDetail.getStudentClass(),
                    account.getEmail(),
                    account.getPhoneNumber(),
                    account.getType());
            return accountDetail;
        }
        StudentDetail studentDetail=new StudentDetail(userId,"NOTFOUND","NOTFOUND");
        StudentAccountDetail accountDetail = new StudentAccountDetail(account.getUserId(),
                account.getFullName(),
                studentDetail.getStudentId(),
                studentDetail.getStudentClass(),
                account.getEmail(),
                account.getPhoneNumber(),
                account.getType());
        return accountDetail;
    }
    public List<StudentInfo>getStudentInfoOfClass(int classId){
        List<StudentInfo>result=new ArrayList<>();
        List<Student>studentList=studentRepository.getStudentsByClassId(classId);
        for(Student student:studentList){
            StudentInfo studentInfo=new StudentInfo();
            StudentAccountDetail accountDetail=getStudentAccountDetail(student.getStudentId());
            studentInfo.setFullName(accountDetail.getFullName());
            studentInfo.setStudentClass(accountDetail.getStudentClass());
            studentInfo.setStudentId(accountDetail.getStudentId());
            studentInfo.setAccountId(accountDetail.getAccountId());
            studentInfo.setClassId(classId);

            result.add(studentInfo);
        }
        return result;
    }
    public ResponseEntity<?>getAccountDetail(int accountId){
        Optional<Account> account=accountRepository.findById(accountId);
        if(account.isPresent()&&account.get().getType()== Role.GV){
            TeacherAccountDetail accountDetail=new TeacherAccountDetail(account.get().getUserId(),account.get().getFullName(),account.get().getEmail(),account.get().getPhoneNumber(),account.get().getType());
            return new ResponseEntity<TeacherAccountDetail>(accountDetail,HttpStatus.OK);
        }
        System.out.println("hello");
        if(account.isPresent()&&account.get().getType()== Role.ADMIN){
            AdminAccountDetail accountDetail=new AdminAccountDetail(account.get().getUserId(),account.get().getFullName(),account.get().getEmail(),account.get().getPhoneNumber(),account.get().getType());
            return new ResponseEntity<AdminAccountDetail>(accountDetail,HttpStatus.OK);
        }
        if(account.isPresent()&&account.get().getType()== Role.SV){
            StudentAccountDetail accountDetail=getStudentAccountDetail(accountId);
            return new ResponseEntity<StudentAccountDetail>(accountDetail,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
