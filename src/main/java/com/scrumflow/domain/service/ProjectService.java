package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.domain.mapper.ProjectMapper;
import com.scrumflow.domain.service.validation.ProjectUtilities;
import com.scrumflow.infrastructure.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUtilities projectUtilities;

    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        projectUtilities.validateProjectFields(projectRequestDTO);
        final var project = projectMapper.dtoToEntity(projectRequestDTO);
        return projectMapper.entityToDto(projectRepository.save(project));
    }

    public List<ProjectResponseDTO> findAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::entityToDto).toList();
    }

    public ProjectResponseDTO findProjectById(Long projectId) {
        return projectMapper.entityToDto(projectUtilities.getProject(projectId));
    }

    public void updateProject(Long projectId, ProjectRequestDTO projectRequestDTO) {
        projectUtilities.validateProjectFields(projectRequestDTO);
        final var project = projectUtilities.getProject(projectId);
        projectMapper.atualizaDeDto(projectRequestDTO, project);
        projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        final var project = projectUtilities.getProject(projectId);
        project.setActive(false);
        projectRepository.save(project);
    }
}
