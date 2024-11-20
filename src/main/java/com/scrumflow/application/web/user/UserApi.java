package com.scrumflow.application.web.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.application.dto.response.RoleResponseDTO;
import com.scrumflow.application.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "Operações de Usuário")
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserApi {

    @Operation(description = "Retorna uma lista com todos os usuários cadastrados no sistema")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    List<UserResponseDTO> findAll();

    @Operation(description = "Retorna uma lista com os projetos onde o usuário faz parte do time")
    @GetMapping("/{userId}/projects")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_PRODUCT_OWNER", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_MEMBER"})
    List<ProjectResponseDTO> findUserProjects(@PathVariable("userId") Long userId);

    @Operation(description = "Atualiza um usuário")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    UserResponseDTO updateUser(@PathVariable("userId") Long userId, @RequestBody List<Long> roleIds);

    @Operation(description = "Atualiza um usuário")
    @PatchMapping("/{userId}/notificacoes")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    void updateUserNotifications(
            @PathVariable("userId") Long userId, @RequestBody Boolean sendNotifications);

    @Operation(description = "Retorna uma lista com todas as roles do sistema")
    @GetMapping("/roles")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    List<RoleResponseDTO> findRoles();
}
