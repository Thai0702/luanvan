package com.luanvan.luanvan.subjectclassservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Student {
    @Id
    @Column(name = "uer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(name = "student_id")
    private int studentId;
    @Column(name = "student_class")
    private int studentClass;
}
