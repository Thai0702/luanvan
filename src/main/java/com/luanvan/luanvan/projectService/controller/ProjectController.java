package com.luanvan.luanvan.projectService.controller;


import com.luanvan.luanvan.projectService.model.Project;
import com.luanvan.luanvan.projectService.model.ProjectLog;
import com.luanvan.luanvan.projectService.service.ProjectService;
import com.luanvan.luanvan.projectService.wrapper.CreateProjectForm;
import com.luanvan.luanvan.projectService.wrapper.ProjectLogForm;
import com.luanvan.luanvan.securityService.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ProjectController {
    ProjectService projectService;
    AuthenticationService authenticationService;

    public ProjectController(ProjectService projectService, AuthenticationService authenticationService) {
        this.projectService = projectService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/project/create-project")
    public ResponseEntity<String>createProject(@RequestBody CreateProjectForm formData, @RequestHeader(value = "Authorization")String requestToken){
        formData.setCreatedBy(authenticationService.getUserIdFromToken(requestToken));
        return ResponseEntity.ok(projectService.createProject(formData));
    }
    @DeleteMapping("/api/project/delete-project/{id}")
    public ResponseEntity<String>deleteProject(@PathVariable Integer id){
        return ResponseEntity.ok(projectService.deleteProject(id));
    }
    @PostMapping("/api/project/{id}/project-log")
    public ResponseEntity<String>createProjectLog(@PathVariable Integer id, @RequestBody ProjectLogForm form, @RequestHeader(value = "Authorization")String requestToken){
        form.setCreated_by(authenticationService.getUserIdFromToken(requestToken));
        return ResponseEntity.ok(projectService.createProjectLog(id,form));
    }
    @GetMapping("/api/project/{projectId}/project-log")
    public List<ProjectLog>getAllProjectLog(@PathVariable Integer projectId){
        return projectService.getAllProjectLog(projectId);
    }
    @GetMapping("/api/group/{id}/projects")
    public List<Project>getAllProjectByGroupId(@PathVariable Integer id){

        return projectService.getAllProjectByGroup(id);
    }
    @GetMapping("/api/project/{projectId}")
    public Project getProjectByGroup(@PathVariable Integer projectId){
        return projectService.getOneById(projectId);
    }
    @GetMapping("/api-test/get-all-projects")
    public List<Project>getAllProject(){
        return projectService.getAllProject();
    }
}
