package com.scrumflow.domain.service.utilities;

import org.springframework.stereotype.Component;

import com.scrumflow.application.dto.request.FeatureRequestDTO;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.model.Feature;
import com.scrumflow.infrastructure.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeatureUtilities {
    private final FeatureRepository featureRepository;
    private final ProjectUtilities projectValidationService;

    public Feature getFeature(Long featureId) {
        return featureRepository
                .findById(featureId)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        String.format("Nenhuma feature encontrada para o id: %s", featureId)));
    }

    public void exists(Long featureId) {
        if (!featureRepository.existsById(featureId))
            throw new NotFoundException(
                    String.format("Nenhuma feature encontrada para o id: %s", featureId));
    }

    public void validateFeatureFields(Feature feature, FeatureRequestDTO featureRequestDTO) {
        final var projeto = projectValidationService.getProject(featureRequestDTO.projectId());

        if (!Boolean.TRUE.equals(projeto.getActive()))
            throw new BusinessException("Não é permitido associar funcionalidades a projetos inativos!");

        feature.setProject(projeto);
    }
}
