package com.luanvan.luanvan.accountservice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private int userId;
  @Column(name = "user_password")
    private String password;
  @Column(name = "user_email")
    private String email;
  @Column(name = "user_type")
  @Enumerated(EnumType.STRING)
  private Role type;
  @Column(name = "phone_number")
  private String phoneNumber;
  @Column(name = "full_name")
  private String fullName;

  public Account( String password, String email, Role type, String fullName) {
    this.password = password;
    this.email = email;
    this.type = type;
    this.fullName = fullName;
  }
}
