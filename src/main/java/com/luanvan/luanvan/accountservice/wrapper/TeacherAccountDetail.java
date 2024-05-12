package com.luanvan.luanvan.accountservice.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAccountDetail {
    private int accountId;
    private String fullName;
    private String accountEmail;
    private String phoneNumber;
}
