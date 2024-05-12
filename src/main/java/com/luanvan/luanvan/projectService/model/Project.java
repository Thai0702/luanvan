package com.luanvan.luanvan.projectService.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="project")
public class Project {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "project_of_group")
    private int projectOfGroup;
    @Column(name = "description")
    private String projectDescription;
    @Column(name = "created_by")
    private int createdBy;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "expired_day")
    private Date expiredDay;
    @Column(name = "expired_time")
    private Time expiredTime;

}
