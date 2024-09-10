package com.scrumflow.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.application.dto.response.TeamResponseDTO;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.exception.TeamNotFoundException;
import com.scrumflow.domain.mapper.TeamMapper;
import com.scrumflow.domain.model.Team;
import com.scrumflow.domain.model.User;
import com.scrumflow.infrastructure.repository.ProjectRepository;
import com.scrumflow.infrastructure.repository.TeamRepository;
import com.scrumflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper = Mappers.getMapper(TeamMapper.class);
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TeamResponseDTO createTeam(TeamRequestDTO teamRequestDTO) {

        validateDTOFields(teamRequestDTO);
        List<User> members = userRepository.findAllById(teamRequestDTO.teamMembers());

        List<Long> foundMembers = members.stream().map(User::getId).toList();

        List<Long> missingMembers =
                teamRequestDTO.teamMembers().stream().filter(id -> !foundMembers.contains(id)).toList();

        if (!missingMembers.isEmpty()) {
            throw new NotFoundException(
                    "Não foram encontrados o(s) seguinte(s) usuário(s): "
                            + missingMembers.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        final Team team = teamMapper.dtoToEntity(teamRequestDTO);

        return teamMapper.entityToDto(teamRepository.save(team));
    }

    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO teamRequestDTO) {
        Team team = getTeam(id);

        validateDTOFields(teamRequestDTO);
        teamMapper.updateByDto(teamRequestDTO, team);

        return teamMapper.entityToDto(teamRepository.save(team));
    }

    public void deleteTeam(Long id) {
        Team team = getTeam(id);

        teamRepository.delete(team);
    }

    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findAll().stream().map(teamMapper::entityToDto).toList();
    }

    public TeamResponseDTO getTeamById(Long id) {
        final Team team = getTeam(id);

        return teamMapper.entityToDto(team);
    }

    public void validateDTOFields(TeamRequestDTO teamRequestDTO) {
        projectRepository
                .findById(teamRequestDTO.projectId())
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        "Nenhum projeto encontrado para o id: %s", teamRequestDTO.projectId()));
    }

    private Team getTeam(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }
}
