package com.luanvan.luanvan.subjectclassservice.controller;

import com.luanvan.luanvan.accountservice.model.Account;
import com.luanvan.luanvan.accountservice.model.Role;
import com.luanvan.luanvan.accountservice.service.AccountService;
import com.luanvan.luanvan.groupService.service.GroupService;
import com.luanvan.luanvan.securityService.service.AuthenticationService;
import com.luanvan.luanvan.subjectclassservice.model.SubjectClass;
import com.luanvan.luanvan.subjectclassservice.service.SubjectClassService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
//@RequestMapping("/api-gv/class")
public class SubjectClassController {
    @Autowired
    AccountService accountService;
    private final GroupService groupService;
    SubjectClassService subjectClassService;
    AuthenticationService authenticationService;
    @GetMapping("/api-admin/class")
    public ResponseEntity<List<SubjectClass>> showClass(){
        return  ResponseEntity.ok().body(subjectClassService.findAllClass());
    }
    public SubjectClassController(SubjectClassService subjectClassService, AuthenticationService authenticationService, GroupService groupService) {
        this.subjectClassService = subjectClassService;
        this.authenticationService = authenticationService;
        this.groupService = groupService;
    }
    @GetMapping("/api/class/get/{classId}")
    public ResponseEntity<?> get1SubjectClassById(@PathVariable Integer classId) {
        Optional<SubjectClass> subjectclass = subjectClassService.findById(classId);
        if (subjectclass.isPresent()) {
            return ResponseEntity.ok(subjectclass.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }
    }

    @GetMapping
    public ResponseEntity<List<SubjectClass>> showSubjectClass() {
        return ResponseEntity.ok().body(subjectClassService.findAll());
    }
    @PostMapping("/api-gv/class")
    public ResponseEntity<SubjectClass> createSubjectClass(@RequestBody SubjectClass sc,@RequestHeader(value = "Authorization")String requestToken) {
        if(authenticationService.getUserRole(requestToken)== Role.GV){
            sc.setCreatedBy(authenticationService.getUserIdFromToken(requestToken));
            String inviteCode = groupService.createInviteCode(); // Gọi hàm createInviteCode() để tạo mã mới
            sc.setInviteCode(inviteCode); // Đặt mã vào đối tượng SubjectClass
            SubjectClass subjectClass = subjectClassService.save(sc);
            return ResponseEntity.ok(subjectClass);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/api-gv/class/delete/{subjectClassId}")
    public ResponseEntity<String> deleteSubjectClassById(@PathVariable Integer subjectClassId) {
        try {
            subjectClassService.deleteById(subjectClassId);
            return ResponseEntity.ok("Đã xóa lớp môn học có id là " + subjectClassId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình xóa");
        }
    }
    @PutMapping("/api-gv/class/update/{subjectClassId}")
    public ResponseEntity<String> updateSubjectClass(@PathVariable Integer subjectClassId, @RequestBody SubjectClass subjectClass) {
        try {
            SubjectClass sc = subjectClassService.updateSc(subjectClassId, subjectClass);
            return ResponseEntity.ok("Đã sửa lớp môn học có id là " + subjectClassId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình cập nhật ");
        }
    }
    @GetMapping("/api-gv/class/createdBy/{userId}")
    public List<SubjectClass> getClassCreatedBy(@PathVariable Integer userId) {
        return subjectClassService.getClassByCreatedBy(userId);
    }
    @GetMapping("/studentId/{studentId}")
    public List<SubjectClass> getClassByStudentId(@PathVariable Integer studentId){
        return subjectClassService.getSubjectClassesByStudentId(studentId);
    }
    // tao account bang file excel
//    @PostMapping("/api-gv/class/excel/{classId}")
//    public String importAccountFromExcel(@PathVariable Integer classId,@RequestParam("file") MultipartFile multipartFile) {
//        return accountService.importAccoutFromExcel(classId,multipartFile);
//    }
}
