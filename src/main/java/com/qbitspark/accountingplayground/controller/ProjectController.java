package com.qbitspark.accountingplayground.controller;

import com.qbitspark.accountingplayground.entity.Project;
import com.qbitspark.accountingplayground.payloads.ProjectRequest;
import com.qbitspark.accountingplayground.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public Project createProject(@RequestBody ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/company/{companyId}")
    public List<Project> getProjectsByCompany(@PathVariable UUID companyId) {
        return projectService.getProjectsByCompany(companyId);
    }
}