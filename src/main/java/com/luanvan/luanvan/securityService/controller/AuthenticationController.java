package com.luanvan.luanvan.securityService.controller;


import com.luanvan.luanvan.securityService.model.AuthenticationResponse;
import com.luanvan.luanvan.securityService.model.LoginRequest;
import com.luanvan.luanvan.securityService.model.RegisterRequest;
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
    @PostMapping("/api/authenticate/login")
    public ResponseEntity<AuthenticationResponse>login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/api/authenticate/userId")
    public ResponseEntity<Integer>getUserIdByToken(@RequestHeader(value = "Authorization")String token){
        Integer id=authenticationService.getUserIdFromToken(token);
        return new ResponseEntity<Integer>(id,HttpStatus.OK);
    }
}
