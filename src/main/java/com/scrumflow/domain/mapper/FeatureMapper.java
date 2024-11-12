package com.scrumflow.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.scrumflow.application.dto.request.FeatureRequestDTO;
import com.scrumflow.application.dto.response.FeatureResponseDTO;
import com.scrumflow.application.dto.response.ProjectDetailsResponseDTO;
import com.scrumflow.domain.model.Feature;
import com.scrumflow.domain.model.Sprint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeatureMapper {

    @Mapping(source = "sprints", target = "sprintsId")
    FeatureResponseDTO entityToDto(Feature feature);

    default List<Long> mapSprintsToIds(List<Sprint> sprints) {
        return sprints.stream().map(Sprint::getId).collect(Collectors.toList());
    }

    Feature dtoToEntity(FeatureRequestDTO featureRequestDTO);

    void atualizaDeDto(FeatureRequestDTO featureRequestDTO, @MappingTarget Feature feature);

    default List<ProjectDetailsResponseDTO.FeatureDetailsDTO> entityToFeatureDetailsDTO(
            List<Feature> features) {
        return features.stream().map(this::entityToFeatureDetailsDTO).toList();
    }

    default ProjectDetailsResponseDTO.FeatureDetailsDTO entityToFeatureDetailsDTO(Feature feature) {
        TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

        return ProjectDetailsResponseDTO.FeatureDetailsDTO.builder()
                .id(feature.getId())
                .name(feature.getName())
                .description(feature.getDescription())
                .tasks(taskMapper.entityToDto(feature.getTasks()))
                .build();
    }
}
