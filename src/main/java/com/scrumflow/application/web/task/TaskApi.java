package com.scrumflow.application.web.task;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.scrumflow.application.dto.request.TaskRequestDTO;
import com.scrumflow.application.dto.response.TaskHistoryResponseDTO;
import com.scrumflow.application.dto.response.TaskResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Task", description = "Operações de Tarefa")
@RequestMapping(value = "/api/v1/task", produces = MediaType.APPLICATION_JSON_VALUE)
public interface TaskApi {

    @Operation(description = "Realiza o cadastro de uma tarefa no sistema")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO);

    @Operation(description = "Retorna uma lista com as tarefas cadastradas para uma funcionalidade")
    @GetMapping("/feature/{featureId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    List<TaskResponseDTO> findTasksByFeature(@PathVariable Long featureId);

    @Operation(description = "Retorna uma tarefa cadastrada no sistema")
    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    TaskResponseDTO findTaskById(@PathVariable Long taskId);

    @Operation(description = "Permite a edição de determinada tarefa")
    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    void updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequestDTO taskRequestDTO);

    @Operation(description = "Permite a exclusão de determinada tarefa")
    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    void deleteTask(@PathVariable Long taskId);

    @Operation(description = "Lista o histórico de movimentações de uma tarefa")
    @GetMapping("/{taskId}/history")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    List<TaskHistoryResponseDTO> getTaskHistory(@PathVariable Long taskId);
}
