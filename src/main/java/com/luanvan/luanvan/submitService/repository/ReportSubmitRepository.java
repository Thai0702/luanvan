package com.luanvan.luanvan.submitService.repository;

import com.luanvan.luanvan.submitService.model.ReportSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportSubmitRepository extends JpaRepository<ReportSubmit,Integer> {
}
