package com.scrumflow.application.web.project;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Project", description = "Operações de Projetos")
@RequestMapping(value = "/api/v1/project", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ProjectApi {

    @Operation(description = "Realizar o cadastro de um projeto no sistema")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProjectResponseDTO createProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO);

    @Operation(description = "Retorna uma lista com os projetos cadastrados no sistema")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ProjectResponseDTO> findAllProjects();

    @Operation(description = "Retorna uma lista com os projetos cadastrados no sistema")
    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    ProjectResponseDTO findProjectById(@PathVariable Long projectId);
}
