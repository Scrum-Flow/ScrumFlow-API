package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.filter.FeatureRequestFilterDTO;
import com.scrumflow.application.dto.request.FeatureRequestDTO;
import com.scrumflow.application.dto.response.FeatureResponseDTO;
import com.scrumflow.domain.mapper.FeatureMapper;
import com.scrumflow.domain.service.utilities.FeatureUtilities;
import com.scrumflow.infrastructure.repository.FeatureRepository;
import com.scrumflow.infrastructure.repository.specification.FeatureSpec;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;
    private final FeatureUtilities featureUtilities;

    private final FeatureMapper featureMapper = Mappers.getMapper(FeatureMapper.class);

    public FeatureResponseDTO createFeature(FeatureRequestDTO featureRequestDTO) {
        final var feature = featureMapper.dtoToEntity(featureRequestDTO);
        featureUtilities.validateFeatureFields(feature, featureRequestDTO);
        return featureMapper.entityToDto(featureRepository.save(feature));
    }

    public List<FeatureResponseDTO> findAllFeatures(FeatureRequestFilterDTO filter) {

        return featureRepository.findAll(FeatureSpec.filtrarPor(filter)).stream()
                .map(featureMapper::entityToDto)
                .toList();
    }

    public FeatureResponseDTO findFeatureById(Long featureId) {
        return featureMapper.entityToDto(featureUtilities.getFeature(featureId));
    }

    public void updateFeature(Long featureId, FeatureRequestDTO featureRequestDTO) {
        final var feature = featureUtilities.getFeature(featureId);
        featureUtilities.validateFeatureFields(feature, featureRequestDTO);
        featureMapper.atualizaDeDto(featureRequestDTO, feature);
        featureRepository.save(feature);
    }

    public void deleteFeature(Long featureId) {
        final var feature = featureUtilities.getFeature(featureId);
        featureRepository.delete(feature);
    }

    public List<FeatureResponseDTO> findAllFeaturesWithoutSprints() {
        return featureRepository.findAllBySprintsNull().stream()
                .map(featureMapper::entityToDto)
                .toList();
    }
}
