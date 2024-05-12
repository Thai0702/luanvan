package com.luanvan.luanvan.reportService.repository;

import com.luanvan.luanvan.reportService.model.ReportSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportSubmitRepository extends JpaRepository<ReportSubmit,Integer> {
}
