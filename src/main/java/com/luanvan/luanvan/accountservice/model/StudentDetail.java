package com.luanvan.luanvan.accountservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "student")
public class StudentDetail {
    @Id
    private int userId;
    private String studentId;//DH5200XXXX
    private String studentClass;//D20XXXXX
}
