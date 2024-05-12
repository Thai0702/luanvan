package com.luanvan.luanvan.projectService.repository;

import com.luanvan.luanvan.projectService.model.ProjectLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectLogRepository extends JpaRepository<ProjectLog,Integer> {
    List<ProjectLog> getAllByLogOfProject(Integer logOfProject);
}
