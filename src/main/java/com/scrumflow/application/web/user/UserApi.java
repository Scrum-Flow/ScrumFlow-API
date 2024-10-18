package com.scrumflow.application.web.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.application.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "Operações de Usuário")
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserApi {

    @Operation(description = "Retorna uma lista com todos os usuários cadastrados no sistema")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserResponseDTO> findAll();

    @Operation(description = "Retorna uma lista com os projetos onde o usuário faz parte do time")
    @GetMapping("/{userId}/projects")
    @ResponseStatus(HttpStatus.OK)
    List<ProjectResponseDTO> findUserProjects(@PathVariable("userId") Long userId);

    @Operation(description = "Atualiza um usuário")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserResponseDTO updateUser(@PathVariable("userId") Long userId, @RequestBody List<Long> roleIds);
}
