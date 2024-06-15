package com.luanvan.luanvan.securityService.controller;


import com.luanvan.luanvan.securityService.model.*;
import com.luanvan.luanvan.securityService.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }
    @PostMapping("/api/authenticate/register")
    public ResponseEntity<?>register(@RequestBody RegisterRequest request){
        if(authenticationService.checkExistUser(request)){
            return new ResponseEntity<String>("EXIST USER",HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/api/authenticate/registerAdmin")
    public ResponseEntity<?>registerAdmin(@RequestBody RegisterRequest request){
        if(authenticationService.checkExistUser(request)){
            return new ResponseEntity<String>("EXIST USER",HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }
    @PostMapping("/api/authenticate/login")
    public ResponseEntity<AuthenticationResponse>login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/api/authenticate/userId")
    public ResponseEntity<Integer>getUserIdByToken(@RequestHeader(value = "Authorization")String token){
        Integer id=authenticationService.getUserIdFromToken(token);
        return new ResponseEntity<Integer>(id,HttpStatus.OK);
    }

    @PostMapping("/api/authenticate/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        boolean result = authenticationService.handleForgotPassword(request.getUsername());
        if (result) {
            return ResponseEntity.ok("Kiểm tra OTP trong gmail.");
        } else {
            return new ResponseEntity<>("Không tìm thấy email.", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/api/authenticate/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean result = authenticationService.resetPassword(request.getUsername(), request.getOtp(), request.getNewPassword());
        if (result) {
            return ResponseEntity.ok("Success !");
        } else {
            return ResponseEntity.badRequest().body("Fail to change !");
        }
    }
    // change password
    @PostMapping("/api/authenticate/change-password")
    public ResponseEntity<String> changePassword(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            return new ResponseEntity<>("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
        }
        int accountId = authenticationService.getUserIdFromToken(token);
        boolean isChanged = authenticationService.changePassword(accountId, changePasswordRequest);
        if (isChanged) {
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to change password", HttpStatus.BAD_REQUEST);
        }
    }
}
