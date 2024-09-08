package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.domain.model.Project;
import com.scrumflow.infrastructure.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class)
public interface ProjectMapper {

    ProjectResponseDTO entityToDto(Project project);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    Project dtoToEntity(ProjectRequestDTO projectRequestDTO);

    void atualizaDeDto(ProjectRequestDTO projectRequestDTO, @MappingTarget Project project);
}
