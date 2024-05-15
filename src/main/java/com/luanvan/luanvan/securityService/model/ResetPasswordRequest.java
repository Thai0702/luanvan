package com.luanvan.luanvan.securityService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String username;
    private String otp;
    private String newPassword;
}
