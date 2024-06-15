package com.luanvan.luanvan.subjectclassservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class SubjectClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_class_id")
    private int subjectClassId;

    @Column(name ="subject_name")
    private String subjectName;

    @Column(name = "created_by")
    private int createdBy;
    @Column(name = "full_name")
    private String fullNameCreate;
    @Column(name ="created_at")
    private Timestamp createdAt;
    @Column(name="school_year")
    private String schoolYear;

    @Column(name="number_of_group")
    private int numberOfGroup;

    @Column(name="member_per_group")
    private int memberPerGroup;

    @Column(name="group_register_method")
    private String groupRegisterMethod;
    @Column(name="invitecode")
    private String inviteCode;
}
