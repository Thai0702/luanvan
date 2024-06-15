package com.luanvan.luanvan.reportService.controller;

import com.luanvan.luanvan.reportService.model.ReportRequest;
import com.luanvan.luanvan.reportService.service.ReportService;
import com.luanvan.luanvan.reportService.wrapper.ReportForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    //tao bao cao(gv)
    @PostMapping("/api-gv/report-request")
    public ResponseEntity<String>createReportRequest(@RequestBody ReportRequest reportRequest, @RequestHeader(value = "Authorization") String requestToken){
        ReportRequest savedReportRequest = reportService.saveReportRequest(reportRequest, requestToken);
        if (savedReportRequest != null) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // xoa bao cao(gv)
    @DeleteMapping("/api-gv/report-request/delete/{requestId}")
    public ResponseEntity<String>deleteReportRequest(@PathVariable Integer requestId){
        return ResponseEntity.ok(reportService.deleteReportRequest(requestId));
    }
    // sua bao cao(gv)
    @PutMapping("/api-gv/report-request/update/{requestId}")
    public ResponseEntity<String>updateReportRequest(@PathVariable Integer requestId, @RequestBody ReportForm reportRequest){
        return ResponseEntity.ok(reportService.updateReportRequest(requestId,reportRequest));
    }
    // lay tat ca bao cao(gv)
    @GetMapping("/api-gv/report-request")
    public List<ReportRequest> getAllReportRequest(){
        return reportService.getAllReportRequest();
    }
    //lay danh sach bao cao theo classId
    @GetMapping("/api-gv/report-request/{classId}")
    public  List<ReportRequest> getReportRequestsBySubjectClass(@PathVariable Integer classId){
        return reportService.getReportRequestsBySubjectClass(classId);
    }

}
