package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.filter.SprintFilterDTO;
import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.application.dto.response.SprintResponseDTO;
import com.scrumflow.domain.mapper.SprintMapper;
import com.scrumflow.domain.model.Sprint;
import com.scrumflow.domain.service.utilities.SprintUtilities;
import com.scrumflow.infrastructure.repository.SprintRepository;
import com.scrumflow.infrastructure.repository.specification.SprintSpec;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final SprintUtilities sprintUtilities;

    private final SprintMapper sprintMapper = Mappers.getMapper(SprintMapper.class);

    public SprintResponseDTO createSprint(SprintRequestDTO sprintRequestDTO) {
        Sprint sprint = sprintMapper.dtoToEntity(sprintRequestDTO);

        sprintUtilities.validateSprint(sprint, sprintRequestDTO);

        return sprintMapper.entityToDto(sprintRepository.save(sprint));
    }

    public SprintResponseDTO updateStprint(Long id, SprintRequestDTO sprintRequestDTO) {
        Sprint sprint = sprintUtilities.getSprintById(id);

        sprintUtilities.validateSprint(sprint, sprintRequestDTO);
        sprintMapper.updateByDto(sprintRequestDTO, sprint);

        return sprintMapper.entityToDto(sprintRepository.save(sprint));
    }

    public void deleteSprint(Long id) {
        sprintRepository.delete(sprintUtilities.getSprintById(id));
    }

    public SprintResponseDTO getSprintById(Long id) {
        return sprintMapper.entityToDto(sprintUtilities.getSprintById(id));
    }

    public List<SprintResponseDTO> getSprints(SprintFilterDTO sprintFilterDTO) {
        return sprintRepository.findAll(SprintSpec.filterBy(sprintFilterDTO)).stream()
                .map(sprintMapper::entityToDto)
                .toList();
    }
}
