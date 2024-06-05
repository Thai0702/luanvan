package com.luanvan.luanvan.submitService.controller;

import com.luanvan.luanvan.reportService.wrapper.SubmitForm;
import com.luanvan.luanvan.submitService.model.ReportSubmit;
import com.luanvan.luanvan.submitService.repository.ReportSubmitRepository;
import com.luanvan.luanvan.submitService.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;


@RestController
@RequestMapping("/api/report")
public class SubmitController {
    @Autowired
    private ReportSubmitRepository reportSubmitRepository;

    // Endpoint để nộp báo cáo
    @PostMapping("/submit")
    public ResponseEntity<String> submitReport(
            @RequestParam("submitBy") int submitBy,
            @RequestParam("reportOfRequest") int reportOfRequest,
            @RequestParam("reportTitle") String reportTitle,
            @RequestParam("reportDescription") String reportDescription,
            @RequestParam("attachment") MultipartFile attachment) {

        try {
            // Lưu attachment vào thư mục trên server
            String uploadDir = "uploads/";
            String fileName = attachment.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(attachment.getInputStream(), filePath);

            // Tạo đường dẫn đến attachment
            String attachmentUrl = uploadDir + fileName;

            // Tạo đối tượng ReportSubmit
            ReportSubmit reportSubmit = new ReportSubmit();
            reportSubmit.setSubmitBy(submitBy);
            reportSubmit.setSubmitOfRequest(reportOfRequest);
            reportSubmit.setReportTitle(reportTitle);
            reportSubmit.setReportDescription(reportDescription);

            // Sử dụng constructor của java.sql.Date với tham số là số mili giây từ epoch cho ngày hiện tại
            reportSubmit.setCreatedDate(new Date(System.currentTimeMillis()));

            // Set attachmentUrl cho đối tượng ReportSubmit
            reportSubmit.setUrl(attachmentUrl);

            // Lưu vào cơ sở dữ liệu
            reportSubmitRepository.save(reportSubmit);

            return ResponseEntity.ok("Báo cáo đã được nộp thành công");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi nộp báo cáo");
        }
    }

    // Endpoint để xem attachment
    @GetMapping("/attachment/{submitId}")
    public ResponseEntity<byte[]> getAttachment(@PathVariable int submitId) {
        try {
            // Lấy đường dẫn đến attachment từ cơ sở dữ liệu
            ReportSubmit reportSubmit = reportSubmitRepository.findById(submitId).orElse(null);
            if (reportSubmit == null || reportSubmit.getUrl() == null) {
                return ResponseEntity.notFound().build();
            }

            // Đọc attachment từ đường dẫn
            Path path = Paths.get(reportSubmit.getUrl());
            byte[] data = Files.readAllBytes(path);

            // Trả về attachment
            return ResponseEntity.ok().body(data);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    // Endpoint để xem attachment
//    @GetMapping("/attachment/{submitId}")
//    public ResponseEntity<String> getAttachment(@PathVariable int submitId) {
//        try {
//            // Lấy đường dẫn đến attachment từ cơ sở dữ liệu
//            ReportSubmit reportSubmit = reportSubmitRepository.findById(submitId).orElse(null);
//            if (reportSubmit == null || reportSubmit.getUrl() == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            // Trả về URL của attachment
//            return ResponseEntity.ok().body(reportSubmit.getUrl());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

}
