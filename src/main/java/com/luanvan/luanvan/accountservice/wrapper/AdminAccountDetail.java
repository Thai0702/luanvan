package com.luanvan.luanvan.accountservice.wrapper;

import com.luanvan.luanvan.accountservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAccountDetail {
    private int accountId;
    private String fullName;
    private String accountEmail;
    private String phoneNumber;
    private Role type;
}
