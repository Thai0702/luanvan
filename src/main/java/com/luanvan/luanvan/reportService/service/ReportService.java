package com.luanvan.luanvan.reportService.service;


import com.luanvan.luanvan.reportService.model.ReportRequest;
import com.luanvan.luanvan.submitService.model.ReportSubmit;
import com.luanvan.luanvan.reportService.repository.ReportRequestRepository;
import com.luanvan.luanvan.submitService.repository.ReportSubmitRepository;
import com.luanvan.luanvan.reportService.wrapper.ReportForm;
import com.luanvan.luanvan.securityService.service.AuthenticationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportSubmitRepository reportSubmitRepository;
    private final ReportRequestRepository reportRequestRepository;
    private final AuthenticationService authenticationService;
    public ReportService(ReportSubmitRepository reportSubmitRepository, ReportRequestRepository reportRequestRepository, AuthenticationService authenticationService) {
        this.reportSubmitRepository = reportSubmitRepository;
        this.reportRequestRepository = reportRequestRepository;
        this.authenticationService = authenticationService;
    }
    public ReportRequest saveReportRequest(ReportRequest reportRequest, String requestToken){
        int userId = authenticationService.getUserIdFromToken(requestToken);
        reportRequest.setCreatedBy(userId);
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        reportRequest.setCreatedAt(timestamp);
        return reportRequestRepository.save(reportRequest);
    }


    public String deleteReportRequest(Integer id){
        if(reportRequestRepository.existsById(id)){
            reportRequestRepository.deleteById(id);
            return "Xoa Thanh cong!";
        }
        return "Khong tim thay!";
    }

public String updateReportRequest(Integer id, ReportForm reportForm) {
    Optional<ReportRequest> optionalReportRequest = reportRequestRepository.findById(id);
    if (optionalReportRequest.isPresent()) {
        ReportRequest existingReportRequest = optionalReportRequest.get();
        existingReportRequest.setExpiredTime(reportForm.getExpiredTime());
        existingReportRequest.setExpiredDate(reportForm.getExpiredDate());
        existingReportRequest.setExpiredAction(reportForm.getExpiredAction());
        existingReportRequest.setRequestTile(reportForm.getRequestTile());
        existingReportRequest.setRequestDescription(reportForm.getRequestDescription());
        reportRequestRepository.save(existingReportRequest);
        return "Sửa thành công!";
    } else {
        return "Không tìm thấy!";
    }
}

    public List<ReportRequest> getAllReportRequest() {
        return reportRequestRepository.findAll();
    }

    public List<ReportRequest> getReportRequestsBySubjectClass(Integer classId) {
        return reportRequestRepository.findBySubjectClass(classId);
    }

    public String saveReportSubmit(Integer requestId, String reportTitle, String reportDescription, MultipartFile attachment, String requestToken) {
        if (reportRequestRepository.existsById(requestId)) {
            int userId = authenticationService.getUserIdFromToken(requestToken);

            ReportSubmit reportSubmit = new ReportSubmit();
            reportSubmit.setSubmitBy(userId);
            reportSubmit.setSubmitOfRequest(requestId);
            reportSubmit.setReportTitle(reportTitle);
            reportSubmit.setReportDescription(reportDescription);
            Date currentDate = new Date(System.currentTimeMillis());
            reportSubmit.setCreatedDate(currentDate);
            Time currentTime = new Time(System.currentTimeMillis());
            reportSubmit.setCreatedTime(currentTime);

            try {
                if (attachment != null && !attachment.isEmpty()) {
                    byte[] fileBytes = attachment.getBytes();
                    Blob blob = new javax.sql.rowset.serial.SerialBlob(fileBytes);
                    reportSubmit.setAttachment_URL(blob);
                }
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
                return "Đã xảy ra lỗi khi xử lý tệp đính kèm.";
            }

            reportSubmitRepository.save(reportSubmit);
            return "Nộp báo cáo thành công!";
        }
        return "Không tìm thấy báo cáo có id: " + requestId + " để nộp!";
    }



}
