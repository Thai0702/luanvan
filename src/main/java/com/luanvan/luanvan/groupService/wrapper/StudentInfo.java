package com.luanvan.luanvan.groupService.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo {
    private int classId;
    private int accountId;
    private String studentId;
    private String fullName;
    private String studentClass;
}
