package com.luanvan.luanvan.projectService.service;


import com.luanvan.luanvan.accountservice.repository.StudentDetailRepository;
import com.luanvan.luanvan.projectService.model.Project;
import com.luanvan.luanvan.projectService.model.ProjectLog;
import com.luanvan.luanvan.projectService.repository.ProjectLogRepository;
import com.luanvan.luanvan.projectService.repository.ProjectRepository;
import com.luanvan.luanvan.projectService.wrapper.CreateProjectForm;
import com.luanvan.luanvan.projectService.wrapper.ProjectLogForm;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProjectService {
    ProjectRepository projectRepository;
    ProjectLogRepository projectLogRepository;
    StudentDetailRepository studentDetailRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectLogRepository projectLogRepository, StudentDetailRepository studentDetailRepository) {
        this.projectRepository = projectRepository;
        this.projectLogRepository = projectLogRepository;
        this.studentDetailRepository = studentDetailRepository;
    }

    public String createProject(CreateProjectForm formData){

        Project project=new Project();

        project.setCreatedBy(formData.getCreatedBy());
        project.setProjectName(formData.getProjectName());
        project.setProjectDescription(formData.getProjectDescription());
        project.setProjectOfGroup(formData.getProjectOfGroup());

        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        project.setCreatedAt(timestamp);

        project.setExpiredDay(formData.getExpiredDay());
        project.setExpiredTime(formData.getExpiredTime());

        projectRepository.save(project);
        return "Created !";
    }
    public String deleteProject(Integer id){
        if(projectRepository.existsById(id)){
            projectRepository.deleteById(id);
            return "Deleted !";
        }
        return "Not found !";
    }
    public String createProjectLog(Integer projectId, ProjectLogForm formData){
        if(projectRepository.existsById(projectId)){
            ProjectLog projectLog=new ProjectLog();
            projectLog.setCreatedBy(formData.getCreated_by());
            projectLog.setLogOfProject(projectId);
            projectLog.setLogTitle(formData.getLogTitle());
            projectLog.setLogDescription(formData.getLogDescription());
            projectLog.setAttachment(formData.getAttachment());

            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            projectLog.setCreatedAt(timestamp);

            projectLogRepository.save(projectLog);

            return "Created !";
        }
        return "Not found";
    }
    public List<Project>getAllProjectByGroup(Integer groupId){
        return projectRepository.findByProjectOfGroup(groupId);
    }
    public Project getOneById(Integer projectId){
        return projectRepository.findOneByProjectId(projectId);
    }



    public List<Project>getAllProject(){
        List<Project>projectList=new ArrayList<>();
        projectList=projectRepository.findAll();
        return projectList;
    }

    public List<ProjectLog> getAllProjectLog(Integer projectId) {
        return projectLogRepository.getAllByLogOfProject(projectId);
    }
}
