package com.scrumflow.application.web.sprint;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.scrumflow.application.dto.filter.SprintFilterDTO;
import com.scrumflow.application.dto.request.SprintRequestDTO;
import com.scrumflow.application.dto.response.SprintResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Sprints", description = "Operações das sprints")
@RequestMapping("/api/v1/sprints")
public interface SprintApi {

    @Secured("ROLE_MANAGER")
    @Operation(description = "Realiza o cadastro de uma print em um projeto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    SprintResponseDTO createSprint(@Valid @RequestBody SprintRequestDTO sprintRequestDTO);

    @Operation(description = "Realiza a atualização de uma sprint no sistema")
    @PutMapping("/{sprintId}")
    @ResponseStatus(HttpStatus.OK)
    SprintResponseDTO updateSprint(
            @PathVariable Long sprintId, @Valid @RequestBody SprintRequestDTO sprintRequestDTO);

    @Operation(description = "Remove uma sprint do projeto")
    @DeleteMapping("/{sprintId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSprint(@PathVariable Long sprintId);

    @Operation(description = "Retorna uma lista de sprints de acordo com os filtros utilizados")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<SprintResponseDTO> getSprints(@ParameterObject SprintFilterDTO sprintFilterDTO);

    @Operation(description = "Retorna uma sprint especifica")
    @GetMapping("/{sprintId}")
    @ResponseStatus(HttpStatus.OK)
    SprintResponseDTO getSprintById(@PathVariable Long sprintId);
}
