package com.luanvan.luanvan.projectService.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectForm {
    private String projectName;
    private int projectOfGroup;
    private String projectDescription;
    private int createdBy;
    private Date expiredDay;
    private Time expiredTime;
}