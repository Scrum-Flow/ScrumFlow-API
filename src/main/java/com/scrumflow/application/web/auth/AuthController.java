package com.scrumflow.application.web.auth;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scrumflow.application.dto.request.JWTResponseDTO;
import com.scrumflow.application.dto.request.LoginRequestDTO;
import com.scrumflow.application.dto.request.RegisterRequestDTO;
import com.scrumflow.domain.model.User;
import com.scrumflow.infrastructure.config.security.TokenService;
import com.scrumflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @Override
    public ResponseEntity<JWTResponseDTO> login(@RequestBody LoginRequestDTO body) {
        User user =
                userRepository
                        .findByEmail(body.email())
                        .orElseThrow(() -> new AccessDeniedException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            return ResponseEntity.ok(
                    new JWTResponseDTO(user.getName(), tokenService.generateToken(user)));
        }

        throw new AccessDeniedException("Usuário não autorizado");
    }

    public ResponseEntity<JWTResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = userRepository.findByEmail(body.email());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));

            userRepository.save(newUser);

            return ResponseEntity.ok(
                    new JWTResponseDTO(newUser.getName(), tokenService.generateToken(newUser)));
        }

        throw new AccessDeniedException("Usuário não autorizado");
    }
}
