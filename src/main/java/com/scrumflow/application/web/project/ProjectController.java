package com.scrumflow.application.web.project;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.domain.service.ProjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProjectController implements ProjectApi {

    private final ProjectService projectService;

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        return projectService.createProject(projectRequestDTO);
    }

    @Override
    public List<ProjectResponseDTO> findAllProjects() {
        return projectService.findAllProjects();
    }

    @Override
    public ProjectResponseDTO findProjectById(Long projectId) {
        return projectService.findProjectById(projectId);
    }
}
