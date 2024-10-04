package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.application.dto.response.TeamResponseDTO;
import com.scrumflow.application.dto.response.UserResponseDTO;
import com.scrumflow.domain.model.Project;
import com.scrumflow.domain.model.Team;
import com.scrumflow.domain.model.User;
import com.scrumflow.infrastructure.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, componentModel = "spring")
public interface TeamMapper {
    TeamResponseDTO entityToDto(Team team);

    Team dtoToEntity(TeamRequestDTO teamDto);

    void updateByDto(TeamRequestDTO teamRequestDTO, @MappingTarget Team team);

    ProjectResponseDTO entityToDto(Project project);

    UserResponseDTO entityToDto(User user);
}
