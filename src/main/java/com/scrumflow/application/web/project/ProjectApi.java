package com.scrumflow.application.web.project;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.scrumflow.application.dto.request.ProjectKanbanColumnsRequestDTO;
import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectDetailsResponseDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Project", description = "Operações de Projetos")
@RequestMapping(value = "/api/v1/project", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ProjectApi {

    @Operation(description = "Realiza o cadastro de um projeto no sistema")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_PROJECT_MANAGER"})
    ProjectResponseDTO createProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO);

    @Operation(description = "Retorna uma lista com os projetos cadastrados no sistema")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    List<ProjectResponseDTO> findAllProjects();

    @Operation(description = "Retorna um projeto a partir do ID")
    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PROJECT_MANAGER"})
    ProjectResponseDTO findProjectById(@PathVariable Long projectId);

    @Operation(
            description =
                    "Retorna um projeto junto com suas sprints, funcionalidades e tarefas a partir do ID")
    @GetMapping("/{projectId}/details")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    ProjectDetailsResponseDTO findProjectDetailsById(@PathVariable Long projectId);

    @Operation(description = "Permite a edição de determinado projeto")
    @PutMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PROJECT_MANAGER"})
    void updateProject(
            @PathVariable Long projectId, @Valid @RequestBody ProjectRequestDTO projectRequestDTO);

    @Operation(description = "Permite a inativação de determinado projeto")
    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PROJECT_MANAGER"})
    void deleteProject(@PathVariable Long projectId);

    @Operation(description = "Realiza o cadastro de um projeto no sistema")
    @PostMapping("{projectId}/kanban-columns")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PROJECT_MANAGER", "ROLE_PRODUCT_OWNER"})
    void updateProjectKanbanColumns(
            @PathVariable Long projectId, ProjectKanbanColumnsRequestDTO request);
}
