package com.luanvan.luanvan.reportService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "report_submit")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportSubmit {
    @Id
    @Column(name = "submit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submitId;
    @Column(name = "submit_by")
    private int submitBy;
    @Column(name = "report_of_request")
    private int submitOfRequest;
    @Column(name = "report_title")
    private String reportTitle;
    @Column(name = "report_description")
    private String reportDescription;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name="created_time")
    private Time createdTime;
    @Column(name = "attachment")
    private Blob attachment;
}
