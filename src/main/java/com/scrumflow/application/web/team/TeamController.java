package com.scrumflow.application.web.team;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.application.dto.response.TeamResponseDTO;
import com.scrumflow.domain.service.TeamService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamApi {

    private final TeamService teamService;

    @Override
    public TeamResponseDTO createTeam(TeamRequestDTO team) {
        return teamService.createTeam(team);
    }

    @Override
    public void deleteTeam(Long teamId) {
        teamService.deleteTeam(teamId);
    }

    @Override
    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO team) {
        return teamService.updateTeam(id, team);
    }
    
    @Override
    public List<TeamResponseDTO> getTeams(Long projectId, String name) {
        return teamService.getTeams( projectId, name );
    }
    
    @Override
    public String associateUserToTeam(Long teamId, Long userId) {
        return teamService.associateUserToTeam( teamId, userId);
    }
    
    @Override
    public String disassociateUserFromTeam(Long teamId, Long userId) {
        return teamService.desassociateUserFromTeam(teamId,userId);
    }
}
