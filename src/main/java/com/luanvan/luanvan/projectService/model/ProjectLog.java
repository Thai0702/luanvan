package com.luanvan.luanvan.projectService.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ProjectLog {
    @Id
    @Column(name="log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;
    @Column(name="created_by")
    private int createdBy;
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="log_of_project")
    private int logOfProject;
    @Column(name="log_title")
    private String logTitle;
    @Column(name="log_description")
    private String logDescription;
    @Column(name="attachment")
    private String attachment;
}
