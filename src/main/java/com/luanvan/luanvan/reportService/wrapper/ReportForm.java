package com.luanvan.luanvan.reportService.wrapper;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportForm {
//    private Time expiredTime;
    private String expiredTime;
    private Date expiredDate;

    private String expiredAction;

    private String requestTile;

    private String requestDescription;

    public Time getFormattedExpiredTimeReport() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            java.util.Date time = inputFormat.parse(this.expiredTime);
            String formattedTime = outputFormat.format(time);
            return Time.valueOf(formattedTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
