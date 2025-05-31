package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.entity.Project;
import com.qbitspark.accountingplayground.payloads.ProjectRequest;
import com.qbitspark.accountingplayground.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setProjectName(projectRequest.getName());
        project.setCompanyId(projectRequest.getCompanyId());
        project.setStatus(projectRequest.getStatus());
        project.setBudget(projectRequest.getBudget());

        return projectRepository.save(project);
    }

    public List<Project> getProjectsByCompany(UUID companyId) {
        return projectRepository.findByCompanyId(companyId);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
