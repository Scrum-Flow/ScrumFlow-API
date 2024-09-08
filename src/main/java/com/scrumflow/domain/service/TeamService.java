package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.application.dto.response.TeamResponseDTO;
import com.scrumflow.domain.exception.TeamNotFoundException;
import com.scrumflow.domain.mapper.TeamMapper;
import com.scrumflow.domain.model.Team;
import com.scrumflow.infrastructure.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper = Mappers.getMapper(TeamMapper.class);

    public TeamResponseDTO createTeam(TeamRequestDTO teamRequestDTO) {
        final Team team = teamMapper.dtoToEntity(teamRequestDTO);

        return teamMapper.entityToDto(teamRepository.save(team));
    }

    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO teamRequestDTO) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));

        team.setName(teamRequestDTO.name());
        team.setDescription(teamRequestDTO.description());

        return teamMapper.entityToDto(teamRepository.save(team));
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));

        teamRepository.delete(team);
    }

    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findAll().stream().map(teamMapper::entityToDto).toList();
    }

    public TeamResponseDTO getTeamById(Long id) {
        final Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));

        return teamMapper.entityToDto(team);
    }
}
