package com.luanvan.luanvan.submitService.service;

import com.luanvan.luanvan.submitService.model.ReportSubmit;
import com.luanvan.luanvan.reportService.repository.ReportRequestRepository;
import com.luanvan.luanvan.submitService.repository.ReportSubmitRepository;
import com.luanvan.luanvan.securityService.service.AuthenticationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class SubmitService {
    private final ReportRequestRepository reportRequestRepository;
    private final AuthenticationService authenticationService;
    private final ReportSubmitRepository reportSubmitRepository;
    private final String uploadDirectory = "uploads"; // Thư mục lưu trữ tệp đính kèm

    public SubmitService(ReportRequestRepository reportRequestRepository, AuthenticationService authenticationService, ReportSubmitRepository reportSubmitRepository) {
        this.reportRequestRepository = reportRequestRepository;
        this.authenticationService = authenticationService;
        this.reportSubmitRepository = reportSubmitRepository;
    }

    public String saveReportSubmit(Integer requestId, String reportTitle, String reportDescription, MultipartFile attachment, String requestToken) {
        if (reportRequestRepository.existsById(requestId)) {
            int userId = authenticationService.getUserIdFromToken(requestToken);

            ReportSubmit reportSubmit = new ReportSubmit();
            reportSubmit.setSubmitBy(userId);
            reportSubmit.setSubmitOfRequest(requestId);
            reportSubmit.setReportTitle(reportTitle);
            reportSubmit.setReportDescription(reportDescription);
            reportSubmit.setCreatedDate(new Date(System.currentTimeMillis()));
            reportSubmit.setCreatedTime(new Time(System.currentTimeMillis()));

            try {
                if (attachment != null && !attachment.isEmpty()) {
                    String attachmentFileName = attachment.getOriginalFilename();
                    saveAttachment(attachment, attachmentFileName); // Lưu tệp đính kèm vào thư mục uploads
                    reportSubmit.setUrl(uploadDirectory + "/" + attachmentFileName); // Lưu đường dẫn của tệp đính kèm
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return "Đã xảy ra lỗi khi xử lý tệp đính kèm.";
            }

            reportSubmitRepository.save(reportSubmit);
            return "Nộp báo cáo thành công!";
        }
        return "Không tìm thấy báo cáo có id: " + requestId + " để nộp!";
    }

    public List<ReportSubmit> getAllSubmit() {
        return reportSubmitRepository.findAll();
    }

    private void saveAttachment(MultipartFile attachment, String fileName) throws IOException {
        byte[] bytes = attachment.getBytes();
        Path path = Paths.get(uploadDirectory + "/" + fileName);
        Files.write(path, bytes);
    }
}
