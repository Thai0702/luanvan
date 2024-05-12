package com.luanvan.luanvan.reportService.repository;

import com.luanvan.luanvan.reportService.model.ReportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRequestRepository extends JpaRepository<ReportRequest,Integer> {
    List<ReportRequest> findBySubjectClass(Integer classId);

}
