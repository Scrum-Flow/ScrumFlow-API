package com.scrumflow.application.web.sprint;

import com.scrumflow.application.dto.filter.SprintRequestFilterDTO;
import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.application.dto.response.SprintResponseDTO;
import com.scrumflow.domain.service.SprintService;
import com.scrumflow.domain.service.validation.SprintUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SprintController implements SprintApi 
{
    private final SprintService service;
    
    @Override
    public SprintResponseDTO createSprint(SprintRequestDTO sprintRequestDTO) {
        return service.createSprint(sprintRequestDTO);
    }

    @Override
    public SprintResponseDTO updateSprint( Long sprintId, SprintRequestDTO sprintRequestDTO) {
        return service.updateStprint( sprintId, sprintRequestDTO );
    }

    @Override
    public void deleteSprint(Long sprintId) {
        service.deleteSprint( sprintId );
    }

    @Override
    public SprintResponseDTO getSprints(SprintRequestFilterDTO sprintRequestFilterDTO) {
        return service.getSprints( sprintRequestFilterDTO );
    }

    @Override
    public SprintResponseDTO getSprintById(Long sprintId) {
        return service.getSprintById( sprintId );
    }
}
