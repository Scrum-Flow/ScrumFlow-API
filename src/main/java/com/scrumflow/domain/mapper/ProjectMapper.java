package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.domain.model.Project;
import com.scrumflow.infrastructure.config.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface ProjectMapper {

    ProjectResponseDTO entityToDto(Project project);

    Project dtoToEntity(ProjectRequestDTO projectRequestDTO);
}
