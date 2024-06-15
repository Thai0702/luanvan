package com.luanvan.luanvan.projectService.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private String expiredTime; // Thay đổi kiểu dữ liệu của expiredTime thành String

    // Phương thức để chuyển đổi thời gian thành đối tượng Time
    public Time getFormattedExpiredTime() {
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