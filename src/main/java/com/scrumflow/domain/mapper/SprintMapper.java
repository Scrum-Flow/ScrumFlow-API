package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.application.dto.response.SprintResponseDTO;
import com.scrumflow.domain.model.Sprint;
import com.scrumflow.infrastructure.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, componentModel = "spring")
public interface SprintMapper {
    @Mapping(source = "project.id", target = "projectId")
    SprintResponseDTO entityToDto(Sprint entity);

    Sprint dtoToEntity(SprintRequestDTO dto);

    void updateByDto(SprintRequestDTO dto, @MappingTarget Sprint entity);
}
