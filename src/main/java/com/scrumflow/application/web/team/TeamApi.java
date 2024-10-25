package com.scrumflow.application.web.team;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.scrumflow.application.dto.request.TeamRequestDTO;
import com.scrumflow.application.dto.response.TeamResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Teams", description = "Operações de Times")
@RequestMapping(value = "/api/v1/teams", produces = MediaType.APPLICATION_JSON_VALUE)
public interface TeamApi {

    @Operation(description = "Realiza o cadastro de um time no sistema")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TeamResponseDTO createTeam(@RequestBody @Valid TeamRequestDTO teamRequestDTO);

    @Operation(description = "Realiza a remoção de um time no sistema")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTeam(@PathVariable Long id);

    @Operation(description = "Realiza a atualização do Time no sistema")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TeamResponseDTO updateTeam(
            @PathVariable Long id, @RequestBody @Valid TeamRequestDTO teamRequestDTO);

    @Operation(
            description = "Obtem todos os times podendo passar como parametro project_id ou um name.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<TeamResponseDTO> getTeams(
            @RequestParam(required = false) Long projectId, @RequestParam(required = false) String name);

    @Operation(description = "Obtem um time por ID.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TeamResponseDTO getTeam(@PathVariable Long id);

    @Operation(description = "Adiciona um usuário em um time")
    @PostMapping("/{teamId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    String associateUserToTeam(@PathVariable Long teamId, @PathVariable Long userId);

    @Operation(description = "Remove um usuário de um time")
    @DeleteMapping("/{teamId}/users/{userId}")
    String disassociateUserFromTeam(@PathVariable Long teamId, @PathVariable Long userId);
}
