package com.scrumflow.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.application.dto.response.TeamResponseDTO;
import com.scrumflow.domain.exception.BadRequestException;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.exception.TeamNotFoundException;
import com.scrumflow.domain.mapper.TeamMapper;
import com.scrumflow.domain.model.Project;
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
    private final UserService userService;

    public TeamResponseDTO createTeam(TeamRequestDTO teamRequestDTO) {
        final Team team = teamMapper.dtoToEntity(teamRequestDTO);

        validateDTOFields(team, teamRequestDTO);

        return teamMapper.entityToDto(teamRepository.save(team));
    }

    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO teamRequestDTO) {
        Team team = getTeam(id);

        validateDTOFields(team, teamRequestDTO);
        teamMapper.updateByDto(teamRequestDTO, team);

        return teamMapper.entityToDto(teamRepository.save(team));
    }

    public void deleteTeam(Long id) {
        Team team = getTeam(id);

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
        Team team = getTeam(teamId);
        User user = userService.getUserById(userId);
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
        Team team = getTeam(teamId);
        User user = userService.getUserById(userId);

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

    private void validateDTOFields(Team team, TeamRequestDTO teamRequestDTO) {
        Project project =
                projectRepository
                        .findById(teamRequestDTO.projectId())
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                "Nenhum projeto encontrado para o id: %s", teamRequestDTO.projectId()));

        if (!project.getActive()) {
            throw new BusinessException("Não é permitido associar times a projetos inativos!");
        }

        if (Optional.ofNullable(teamRequestDTO.teamMembers()).isPresent()) {
            List<User> members = userRepository.findAllById(teamRequestDTO.teamMembers());

            List<Long> foundMembers = members.stream().map(User::getId).toList();

            List<Long> missingMembers =
                    teamRequestDTO.teamMembers().stream().filter(id -> !foundMembers.contains(id)).toList();

            if (!missingMembers.isEmpty()) {
                throw new NotFoundException(
                        "Não foram encontrados o(s) seguinte(s) usuário(s): "
                                + missingMembers.stream().map(String::valueOf).collect(Collectors.joining(",")));
            }

            team.getUsers().addAll(members);
        }

        team.setProject(project);
    }

    private Team getTeam(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }
}
