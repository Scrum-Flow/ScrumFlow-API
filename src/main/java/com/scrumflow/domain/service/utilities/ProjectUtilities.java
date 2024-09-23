package com.scrumflow.domain.service.utilities;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.model.Project;
import com.scrumflow.infrastructure.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectUtilities {
    private final ProjectRepository projectRepository;

    public Project validateActiveProject(Long projectId) {
        Project project = getProject(projectId);

        if (!project.getActive()) {
            throw new BusinessException("O projeto " + project.getName() + " está inativo");
        }

        return project;
    }

    public Project getProject(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        String.format("Nenhum projeto encontrado para o id: %s", projectId)));
    }

    public void validateProjectFields(ProjectRequestDTO projectRequestDTO) {
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
}
