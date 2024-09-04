package com.scrumflow.application.web.auth;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scrumflow.application.dto.request.LoginRequestDTO;
import com.scrumflow.application.dto.request.RegisterRequestDTO;
import com.scrumflow.application.dto.response.JWTResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth", description = "Operações de Autenticação")
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuthApi {

    @Operation(description = "Realizar login no sistema")
    @PostMapping("/login")
    JWTResponseDTO login(@RequestBody LoginRequestDTO body);

    @Operation(description = "Realizar cadastro no sistema")
    @PostMapping("/register")
    JWTResponseDTO register(@RequestBody RegisterRequestDTO body);
}
