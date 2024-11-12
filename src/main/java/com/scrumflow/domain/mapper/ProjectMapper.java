package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectDetailsResponseDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.domain.model.Project;
import com.scrumflow.infrastructure.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = BaseMapperConfig.class)
public interface ProjectMapper {

    ProjectResponseDTO entityToDto(Project project);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "active", target = "active")
    Project dtoToEntity(ProjectRequestDTO projectRequestDTO);

    void atualizaDeDto(ProjectRequestDTO projectRequestDTO, @MappingTarget Project project);

    default ProjectDetailsResponseDTO entityToProjectDetailsDto(Project project) {
        SprintMapper sprintMapper = Mappers.getMapper(SprintMapper.class);

        return ProjectDetailsResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .sprints(sprintMapper.entityToSprintDetailsDTO(project.getSprints()))
                .build();
    }
}
