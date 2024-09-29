package com.scrumflow.application.web.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
}
