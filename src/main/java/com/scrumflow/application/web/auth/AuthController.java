package com.scrumflow.application.web.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scrumflow.application.dto.request.JWTResponseDTO;
import com.scrumflow.application.dto.request.LoginRequestDTO;
import com.scrumflow.application.dto.request.RegisterRequestDTO;
import com.scrumflow.domain.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;

    @Override
    public ResponseEntity<JWTResponseDTO> login(@RequestBody LoginRequestDTO body) {
        return ResponseEntity.ok(userService.login(body));
    }

    public ResponseEntity<JWTResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        return ResponseEntity.ok(userService.registerUser(body));
    }
}
