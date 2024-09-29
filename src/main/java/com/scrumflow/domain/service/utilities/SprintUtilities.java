package com.scrumflow.domain.service.utilities;

import org.springframework.stereotype.Component;

import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.domain.dto.ErrorsDTO;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;
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

        ErrorsDTO errorsDTO = validateDates(sprint, p, new ErrorsDTO());

        if (errorsDTO.hasErrors()) {
            throw new BusinessException(errorsDTO.getErrors());
        }

        sprint.setProject(p);
    }

    private ErrorsDTO validateDates(Sprint sprint, Project p, ErrorsDTO errorsDTO) {
        if (sprint.getStartDate().isBefore(p.getStartDate())) {
            errorsDTO.addError(
                    "A data de início da sprint não pode ser anterior a data de início do projeto");
        }

        if (sprint.getStartDate().isAfter(p.getEndDate())) {
            errorsDTO.addError("A data de início da sprint não pode ser posterior ao término do projeto");
        }

        if (sprint.getStartDate().isAfter(sprint.getEndDate())) {
            errorsDTO.addError("A data incial não pode ser posterior a data final");
        }

        return errorsDTO;
    }
}
