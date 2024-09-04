package com.scrumflow.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.mapper.ProjectMapper;
import com.scrumflow.infrastructure.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        this.validateProjectFields(projectRequestDTO);
        final var project = projectMapper.dtoToEntity(projectRequestDTO);
        return projectMapper.entityToDto(projectRepository.save(project));
    }

    private void validateProjectFields(ProjectRequestDTO projectRequestDTO) {
        Optional.ofNullable(projectRequestDTO.startDate())
                .flatMap(
                        start ->
                                Optional.ofNullable(projectRequestDTO.endDate()).filter(end -> end.isBefore(start)))
                .ifPresent(
                        end -> {
                            throw new BusinessException(
                                    "A data de término não pode ser anterior à data de início.");
                        });
    }

    public List<ProjectResponseDTO> findAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::entityToDto).toList();
    }

    public ProjectResponseDTO findProjectById(Long projectId) {
        final var project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                String.format("Nenhum projeot encontrado para o id: %s", projectId)));
        return projectMapper.entityToDto(project);
    }
}
