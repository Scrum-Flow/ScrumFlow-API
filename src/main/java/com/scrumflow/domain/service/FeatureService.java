package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.filter.FeatureRequestFilterDTO;
import com.scrumflow.application.dto.request.FeatureRequestDTO;
import com.scrumflow.application.dto.response.FeatureResponseDTO;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.mapper.FeatureMapper;
import com.scrumflow.domain.model.Feature;
import com.scrumflow.domain.model.Project;
import com.scrumflow.infrastructure.repository.FeatureRepository;
import com.scrumflow.infrastructure.repository.ProjectRepository;
import com.scrumflow.infrastructure.repository.specification.FeatureSpec;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;
    private final ProjectRepository projectRepository;
    private final FeatureMapper featureMapper = Mappers.getMapper(FeatureMapper.class);

    public FeatureResponseDTO createFeature(FeatureRequestDTO featureRequestDTO) {
        final var feature = featureMapper.dtoToEntity(featureRequestDTO);
        validateFeatureFields(feature, featureRequestDTO);
        return featureMapper.entityToDto(featureRepository.save(feature));
    }

    public List<FeatureResponseDTO> findAllFeatures(FeatureRequestFilterDTO filter) {

        return featureRepository.findAll(FeatureSpec.filtrarPor(filter)).stream()
                .map(featureMapper::entityToDto)
                .toList();
    }

    public FeatureResponseDTO findFeatureById(Long featureId) {
        return featureMapper.entityToDto(getFeature(featureId));
    }

    public void updateFeature(Long featureId, FeatureRequestDTO featureRequestDTO) {
        final var feature = getFeature(featureId);
        validateFeatureFields(feature, featureRequestDTO);
        featureMapper.atualizaDeDto(featureRequestDTO, feature);
        featureRepository.save(feature);
    }

    public void deleteFeature(Long featureId) {
        final var feature = getFeature(featureId);
        featureRepository.delete(feature);
    }

    private Feature getFeature(Long featureId) {
        return featureRepository
                .findById(featureId)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        String.format("Nenhuma feature encontrada para o id: %s", featureId)));
    }

    private void validateFeatureFields(Feature feature, FeatureRequestDTO featureRequestDTO) {
        final var projeto = getProject(featureRequestDTO.projectId());
        if (!Boolean.TRUE.equals(projeto.getActive()))
            throw new BusinessException("Não é permitido associar funiconalidades a projetos inativos!");

        feature.setProject(projeto);
    }

    private Project getProject(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        String.format("Nenhum projeto encontrado para o id: %s", projectId)));
    }
}
