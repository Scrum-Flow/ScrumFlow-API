package com.scrumflow.application.web.sprint;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.scrumflow.application.dto.filter.SprintFilterDTO;
import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.application.dto.response.SprintResponseDTO;
import com.scrumflow.domain.service.SprintService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SprintController implements SprintApi {
    private final SprintService service;

    @Override
    public SprintResponseDTO createSprint(SprintRequestDTO sprintRequestDTO) {
        return service.createSprint(sprintRequestDTO);
    }

    @Override
    public SprintResponseDTO updateSprint(Long sprintId, SprintRequestDTO sprintRequestDTO) {
        return service.updateStprint(sprintId, sprintRequestDTO);
    }

    @Override
    public void deleteSprint(Long sprintId) {
        service.deleteSprint(sprintId);
    }

    @Override
    public List<SprintResponseDTO> getSprints(SprintFilterDTO sprintFilterDTO) {
        return service.getSprints(sprintFilterDTO);
    }

    @Override
    public SprintResponseDTO getSprintById(Long sprintId) {
        return service.getSprintById(sprintId);
    }
}
