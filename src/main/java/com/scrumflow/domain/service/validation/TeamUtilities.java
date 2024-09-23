package com.scrumflow.domain.service.validation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.exception.TeamNotFoundException;
import com.scrumflow.domain.model.Project;
import com.scrumflow.domain.model.Team;
import com.scrumflow.domain.model.User;
import com.scrumflow.infrastructure.repository.TeamRepository;
import com.scrumflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TeamUtilities {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ProjectUtilities projectValidationService;

    public void validateDTOFields(Team team, TeamRequestDTO teamRequestDTO) {
        Project project = projectValidationService.validateActiveProject(teamRequestDTO.projectId());

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

    public Team getTeam(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }
}
