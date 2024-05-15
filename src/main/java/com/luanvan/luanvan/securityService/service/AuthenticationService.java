package com.luanvan.luanvan.securityService.service;



import com.luanvan.luanvan.accountservice.model.Role;
import com.luanvan.luanvan.securityService.entity.User;
import com.luanvan.luanvan.securityService.model.AuthenticationResponse;
import com.luanvan.luanvan.securityService.model.LoginRequest;
import com.luanvan.luanvan.securityService.model.RegisterRequest;
import com.luanvan.luanvan.securityService.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


@Service
@Transactional
public class AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(RegisterRequest request){
        User user=new User();
        user.setFullname(request.getFullname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setRole(Role.GV);

        userRepo.save(user);
        String token=jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user=userRepo.findByUsername(request.getUsername()).orElseThrow();
        var token=jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
    public Integer getUserIdFromToken(String token){
        String username=jwtService.extractUsername(token.substring(7));
        var user=userRepo.findByUsername(username);
        if(user.isPresent()){
            return user.get().getId();
        }
        return 0;
    }

    public Boolean checkExistUser(RegisterRequest request){
        if(userRepo.existsUsersByUsername(request.getUsername())){
            return true;
        }
        return false;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;



    public boolean handleForgotPassword(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        String otp = generateOtp();
        saveOtp(otp, user);
        String message = "Your OTP for password reset is: " + otp;
        sendEmail(user.getUsername(), "Password Reset OTP", message);
        return true;
    }

    private void saveOtp(String otp, User user) {
        user.setOtp(otp);
        user.setResetOtpExpiry(LocalDateTime.now().plusMinutes(10)); // OTP expires after 10 minutes
        userRepository.save(user);
    }

    private String generateOtp() {
        int otpLength = 6;
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otp.toString();
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    public boolean resetPassword(String username, String otp, String newPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            System.out.println("User not found: " + username);
            return false;
        }
        User user = userOptional.get();
        boolean isOtpValid = otp.equals(user.getOtp());
        boolean isTokenExpired = user.getResetOtpExpiry().isBefore(LocalDateTime.now());
//        System.out.println("OTP provided: " + otp);
//        System.out.println("OTP in database: " + user.getResetToken());
//        System.out.println("Is OTP valid: " + isOtpValid);
//        System.out.println("Is token expired: " + isTokenExpired);
        if (isOtpValid) {
            if (isOtpValid && isTokenExpired) {
                user.setPassword(passwordEncoder.encode(newPassword)); // Ensure password is encoded
                user.setOtp(null);
                user.setResetOtpExpiry(null);
                userRepository.save(user);
                return true;
            }
        }
        return  false;
    }


}