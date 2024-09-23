package com.scrumflow.domain.service.validation;

import org.springframework.stereotype.Component;

import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.model.Errors;
import com.scrumflow.domain.model.Project;
import com.scrumflow.domain.model.Sprint;
import com.scrumflow.infrastructure.repository.SprintRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SprintUtilities {
    private final SprintRepository sprintRepository;
    private final ProjectUtilities projectValidationService;

    public Sprint getSprintById(Long id) {
        return sprintRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        String.format("Nenhuma sprint encontrada para o id: %s", id)));
    }

    public void validateSprint(Sprint sprint, SprintRequestDTO sprintRequestDTO) {
        Project p = projectValidationService.validateActiveProject(sprintRequestDTO.projectId());

        Errors errors = validateDates(sprint, p, new Errors());

        if (errors.hasErrors()) {
            throw new BusinessException(errors.getErrorMessage());
        }

        sprint.setProject(p);
    }

    private Errors validateDates(Sprint sprint, Project p, Errors errors) {
        if (sprint.getStartDate().isBefore(p.getStartDate())) {
            errors.addError(
                    "A data de início da sprint não pode ser anterior a data de início do projeto");
        }

        if (sprint.getStartDate().isAfter(p.getEndDate())) {
            errors.addError("A data de início da sprint não pode ser posterior ao termino do projeto");
        }

        if (sprint.getStartDate().isAfter(sprint.getEndDate())) {
            errors.addError("A data incial não pode ser posterior a data final");
        }

        return errors;
    }
}
