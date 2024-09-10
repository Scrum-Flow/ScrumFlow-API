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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTeam(@PathVariable Long id);

    @Operation(description = "Realiza a atualização do Time no sistema")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TeamResponseDTO updateTeam(
            @PathVariable Long id, @RequestBody @Valid TeamRequestDTO teamRequestDTO);

    @Operation(description = "Obter todos os times")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<TeamResponseDTO> getTeams();

    @Operation(description = "Obter um time pelo ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TeamResponseDTO getTeamById(@PathVariable Long id);
}
