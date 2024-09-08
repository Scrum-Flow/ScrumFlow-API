package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.application.dto.response.TeamResponseDTO;
import com.scrumflow.domain.model.Team;
import com.scrumflow.infrastructure.config.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface TeamMapper {
    TeamResponseDTO entityToDto(Team team);

    Team dtoToEntity(TeamRequestDTO teamDto);
}
