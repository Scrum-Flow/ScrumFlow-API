package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.filter.SprintFilterDTO;
import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.application.dto.response.FeatureResponseDTO;
import com.scrumflow.application.dto.response.SprintResponseDTO;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.mapper.FeatureMapper;
import com.scrumflow.domain.mapper.SprintMapper;
import com.scrumflow.domain.model.Feature;
import com.scrumflow.domain.model.Sprint;
import com.scrumflow.domain.service.utilities.FeatureUtilities;
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
    private final FeatureMapper featureMapper = Mappers.getMapper(FeatureMapper.class);
    private final FeatureUtilities featureUtilities;

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

    public void associateFeature(Long sprintId, Long featureId) {
        Sprint sprint = sprintUtilities.getSprintById(sprintId);
        Feature feature = featureUtilities.getFeature(featureId);

        if (sprint.getFeatures().contains(feature)) {
            throw new BusinessException("Feature already associated with this sprint");
        }

        sprint.getFeatures().add(feature);
        sprintRepository.save(sprint);
    }

    public void desassociateFeature(Long sprintId, Long featureId) {
        Sprint sprint = sprintUtilities.getSprintById(sprintId);
        Feature feature = featureUtilities.getFeature(featureId);

        if (!sprint.getFeatures().contains(feature)) {
            throw new BusinessException("Feature does not associated with this sprint");
        }

        sprint.getFeatures().remove(feature);
        sprintRepository.save(sprint);
    }

    public List<FeatureResponseDTO> getAssociatedFeatures(Long sprintId) {
        Sprint sprint = sprintUtilities.getSprintById(sprintId);

        return sprint.getFeatures().stream().map(featureMapper::entityToDto).toList();
    }
}
