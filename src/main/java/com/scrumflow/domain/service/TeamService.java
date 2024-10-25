package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.application.dto.response.TeamResponseDTO;
import com.scrumflow.domain.exception.BadRequestException;
import com.scrumflow.domain.mapper.TeamMapper;
import com.scrumflow.domain.model.Team;
import com.scrumflow.domain.model.User;
import com.scrumflow.domain.service.utilities.TeamUtilities;
import com.scrumflow.domain.service.utilities.UserUtilities;
import com.scrumflow.infrastructure.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper = Mappers.getMapper(TeamMapper.class);
    private final TeamUtilities teamUtilities;
    private final UserUtilities userUtilities;

    public TeamResponseDTO createTeam(TeamRequestDTO teamRequestDTO) {
        final Team team = teamMapper.dtoToEntity(teamRequestDTO);

        teamUtilities.validateDTOFields(team, teamRequestDTO);

        return teamMapper.entityToDto(teamRepository.save(team));
    }

    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO teamRequestDTO) {
        Team team = teamUtilities.getTeam(id);

        teamUtilities.validateDTOFields(team, teamRequestDTO);
        teamMapper.updateByDto(teamRequestDTO, team);

        return teamMapper.entityToDto(teamRepository.save(team));
    }

    public void deleteTeam(Long id) {
        Team team = teamUtilities.getTeam(id);

        teamRepository.delete(team);
    }

    public List<TeamResponseDTO> getTeams(Long projectId, String name) {
        List<Team> teams;
        if (projectId != null) {
            if (projectId <= 0) {
                throw new BadRequestException("Informe um id de projeto maior que zero!");
            }

            teams = teamRepository.findByProjectId(projectId);
        } else if (name != null && !name.isEmpty()) {
            teams = teamRepository.findByNameContaining(name);
        } else {
            teams = teamRepository.findAll();
        }

        return teams.stream().map(teamMapper::entityToDto).toList();
    }

    public String associateUserToTeam(Long teamId, Long userId) {
        Team team = teamUtilities.getTeam(teamId);
        User user = userUtilities.getUserById(userId);
        team.getUsers().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .ifPresentOrElse(
                        u -> {
                            throw new BadRequestException("Usuário já está associado no time!");
                        },
                        () -> {
                            team.getUsers().add(user);
                            teamRepository.save(team);
                        });

        return "Usuário adicionado no time com sucesso!";
    }

    public String desassociateUserFromTeam(Long teamId, Long userId) {
        Team team = teamUtilities.getTeam(teamId);
        User user = userUtilities.getUserById(userId);

        team.getUsers().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .ifPresentOrElse(
                        u -> {
                            team.getUsers().remove(user);
                            teamRepository.save(team);
                        },
                        () -> {
                            throw new BadRequestException("Usuário não está associado ao time!");
                        });

        return "Usuário removido do time com sucesso!";
    }

    public TeamResponseDTO getTeam(Long id) {
        return teamMapper.entityToDto(teamUtilities.getTeam(id));
    }
}
