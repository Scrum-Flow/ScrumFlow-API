package com.scrumflow.application.web.auth;

import com.scrumflow.application.dto.response.JWTResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scrumflow.application.dto.request.LoginRequestDTO;
import com.scrumflow.application.dto.request.RegisterRequestDTO;
import com.scrumflow.domain.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;

    @Override
    public JWTResponseDTO login(@RequestBody LoginRequestDTO body) {
        return userService.login(body);
    }

    @Override
    public JWTResponseDTO register(@RequestBody RegisterRequestDTO body) {
        return userService.registerUser(body);
    }
}
