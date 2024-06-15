package com.luanvan.luanvan.accountservice.controller;

import com.luanvan.luanvan.accountservice.model.Account;
import com.luanvan.luanvan.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api-admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> showAccount(){
        return  ResponseEntity.ok().body(accountService.findAll());
    }
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account ac){
        Account account =accountService.save(ac);
        return ResponseEntity.ok(account);
    }
    // xoa tai khoan
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHAccountById(@PathVariable Integer id) {
        try {
            accountService.deleteById(id);
            return ResponseEntity.ok("Đã xóa tài khoản có id là " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình xóa tài khoản");
        }
    }

    //sua tai khoan
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Integer id, @RequestBody Account account) {
        try {
            Account ac = accountService.updateAc(id, account);
            return ResponseEntity.ok("Đã sửa tài khoản có id là " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình cập nhật tài khoản");
        }
    }
    // tao account bang file excel
//    @PostMapping("/class/excel/{classId}")
//    public String importAccountFromExcel(@PathVariable Integer classId,@RequestParam("file") MultipartFile multipartFile) {
//        return accountService.importAccoutFromExcel(classId,multipartFile);
//    }
}
