package com.scrumflow.domain.mapper;

import java.util.List;

import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.application.dto.response.ProjectDetailsResponseDTO;
import com.scrumflow.application.dto.response.SprintResponseDTO;
import com.scrumflow.domain.model.Sprint;
import com.scrumflow.infrastructure.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = BaseMapperConfig.class, componentModel = "spring")
public interface SprintMapper {
    @Mapping(source = "project.id", target = "projectId")
    SprintResponseDTO entityToDto(Sprint entity);

    Sprint dtoToEntity(SprintRequestDTO dto);

    void updateByDto(SprintRequestDTO dto, @MappingTarget Sprint entity);

    default List<ProjectDetailsResponseDTO.SprintDetailsDTO> entityToSprintDetailsDTO(
            List<Sprint> sprints) {
        return sprints.stream().map(this::entityToSprintDetailsDTO).toList();
    }

    default ProjectDetailsResponseDTO.SprintDetailsDTO entityToSprintDetailsDTO(Sprint sprint) {
        FeatureMapper featureMapper = Mappers.getMapper(FeatureMapper.class);

        return ProjectDetailsResponseDTO.SprintDetailsDTO.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .description(sprint.getDescription())
                .features(featureMapper.entityToFeatureDetailsDTO(sprint.getFeatures()))
                .build();
    }
}
