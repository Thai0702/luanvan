package com.luanvan.luanvan.reportService.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;



@Entity
@Table(name = "report_request")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportRequest {
    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;
    @Column(name = "created_by")
    private int createdBy;
    @Column(name = "subject_class")
    private int subjectClass;
    @Column(name = "request_of_project")
    private int requestOfProject;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "expired_time")
    private Time expiredTime;
    @Column(name = "expired_date")
    private Date expiredDate;
    @Column(name = "expired_action")
    private String expiredAction;
    @Column(name = "request_title")
    private String requestTile;
    @Column(name = "request_description")
    private String requestDescription;
}
