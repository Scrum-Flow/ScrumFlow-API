package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.request.FeatureRequestDTO;
import com.scrumflow.application.dto.response.FeatureResponseDTO;
import com.scrumflow.domain.model.Feature;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface FeatureMapper {

    FeatureResponseDTO entityToDto(Feature feature);

    Feature dtoToEntity(FeatureRequestDTO featureRequestDTO);

    void atualizaDeDto(FeatureRequestDTO featureRequestDTO, @MappingTarget Feature feature);
}
