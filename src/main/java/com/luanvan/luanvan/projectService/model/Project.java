package com.luanvan.luanvan.projectService.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private String expiredTime; // Thay đổi kiểu dữ liệu của expiredTime thành String

    // Phương thức để chuyển đổi thời gian thành đối tượng Time
    public Time getFormattedExpiredTime2() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");

        try {
            // Chuyển đổi chuỗi thành đối tượng Date
            java.util.Date time = inputFormat.parse(this.expiredTime);
            // Lấy thời gian dưới dạng chuỗi theo định dạng HH:mm:ss
            String formattedTime = outputFormat.format(time);
            // Chuyển đổi chuỗi thành đối tượng Time
            return Time.valueOf(formattedTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
