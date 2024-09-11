package com.scrumflow.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import com.scrumflow.domain.exception.BadRequestException;
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
    private final UserService userService;
    
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

    public List<TeamResponseDTO> getTeams( Long projectId, String name ) 
    {
         List<Team> teams;
        if ( projectId != null ) 
        {
            if ( projectId <= 0 )
            {
                throw new BadRequestException( "Informe um id de projeto maior que zero!" );    
            }
            
            teams = teamRepository.findByProjectId(projectId);
        } else if ( name != null && !name.isEmpty() ) {
            teams = teamRepository.findByNameContaining(name);
        } else {
            teams = teamRepository.findAll();
        }

        return teams.stream().map(teamMapper::entityToDto).toList();
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
    
    public String associateUserToTeam(Long teamId, Long userId) 
    {
        Team team = getTeam(teamId);
        User user = userService.getUserById(userId);
        
        /* precisa ser feito 
        * team.getUsers().contains(user) -> bad request pois ele já está associado
        * 
        * salva a associação
        * */
        
        return "Usuário adicionado no time com sucesso!";
    }
    
    public String desassociateUserFromTeam(Long teamId, Long userId) 
    {
         Team team = getTeam(teamId);
        User user = userService.getUserById(userId);
        
        /* precisa ser feito 
        * !team.getUsers().contains(user) -> bad request pois usuário não existe no time
        * 
        * delete associação
        * */
        
        return "Usuário removido do time com sucesso!";
    }
}
