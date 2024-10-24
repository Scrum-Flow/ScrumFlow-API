package com.scrumflow.application.web.feature;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.scrumflow.application.dto.filter.FeatureRequestFilterDTO;
import com.scrumflow.application.dto.request.FeatureRequestDTO;
import com.scrumflow.application.dto.response.FeatureResponseDTO;
import com.scrumflow.domain.service.FeatureService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FeatureController implements FeatureApi {

    private final FeatureService featureService;

    @Override
    public FeatureResponseDTO createFeature(FeatureRequestDTO featureRequestDTO) {
        return featureService.createFeature(featureRequestDTO);
    }

    @Override
    public List<FeatureResponseDTO> findAllFeatures(FeatureRequestFilterDTO filter) {
        return featureService.findAllFeatures(filter);
    }

    @Override
    public FeatureResponseDTO findFeatureById(Long featureId) {
        return featureService.findFeatureById(featureId);
    }

    @Override
    public void updateFeature(Long featureId, FeatureRequestDTO featureRequestDTO) {
        featureService.updateFeature(featureId, featureRequestDTO);
    }

    @Override
    public void deleteFeature(Long featureId) {
        featureService.deleteFeature(featureId);
    }
}
