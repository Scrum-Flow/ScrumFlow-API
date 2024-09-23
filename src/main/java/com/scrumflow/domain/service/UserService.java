package com.scrumflow.domain.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.LoginRequestDTO;
import com.scrumflow.application.dto.request.RegisterRequestDTO;
import com.scrumflow.application.dto.response.LoginResponseDTO;
import com.scrumflow.domain.exception.BadRequestException;
import com.scrumflow.domain.exception.InvalidCredentialsException;
import com.scrumflow.domain.model.Role;
import com.scrumflow.domain.model.User;
import com.scrumflow.domain.service.utilities.UserUtilities;
import com.scrumflow.infrastructure.config.security.TokenService;
import com.scrumflow.infrastructure.repository.RoleRepository;
import com.scrumflow.infrastructure.repository.UserRepository;
import com.scrumflow.infrastructure.utilities.RegisterValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final UserUtilities userUtilities;

    public LoginResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {

        if (!RegisterValidator.isEmailValid((registerRequestDTO.email()))) {
            throw new InvalidCredentialsException("Email inválido!");
        }

        Optional<User> user = userRepository.findByEmail(registerRequestDTO.email());

        if (user.isPresent()) {
            throw new InvalidCredentialsException("Email em uso!");
        }

        if (!RegisterValidator.isPassValid(registerRequestDTO.password())) {
            throw new InvalidCredentialsException("Senha inválida!");
        }

        Role role = userUtilities.getRoleByName(registerRequestDTO.role());

        User newUser = new User();
        newUser.setName(registerRequestDTO.name());
        newUser.setEmail(registerRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        newUser.getRoles().add(role);

        userRepository.save(newUser);

        return new LoginResponseDTO(
                newUser.getName(),
                newUser.getEmail(),
                tokenService.generateToken(newUser),
                userUtilities.getUserRoles(newUser));
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<User> user = userRepository.findByEmail(loginRequestDTO.email());

        return user.filter(u -> passwordEncoder.matches(loginRequestDTO.password(), u.getPassword()))
                .map(
                        u ->
                                new LoginResponseDTO(
                                        u.getName(),
                                        u.getEmail(),
                                        tokenService.generateToken(u),
                                        userUtilities.getUserRoles(u)))
                .orElseThrow(() -> new InvalidCredentialsException("Usuário ou senha inválidos"));
    }

    public String assignRoleToUser(Long userId, Long roleId) {
        User user = userUtilities.getUserById(userId);
        Role role = userUtilities.getRoleById(roleId);

        user.getRoles().stream()
                .filter(r -> Objects.equals(r.getId(), roleId))
                .findFirst()
                .ifPresentOrElse(
                        r -> {
                            throw new BadRequestException("Usuário já possui a role!");
                        },
                        () -> {
                            user.getRoles().add(role);
                            userRepository.save(user);
                        });

        return "Role adicionada com sucesso";
    }

    public String removeRoleFromUser(Long userId, Long roleId) {
        User user = userUtilities.getUserById(userId);
        Role role = userUtilities.getRoleById(roleId);

        user.getRoles().stream()
                .filter(r -> Objects.equals(r.getId(), roleId))
                .findFirst()
                .ifPresentOrElse(
                        r -> {
                            user.getRoles().remove(role);
                            userRepository.save(user);
                        },
                        () -> {
                            throw new BadRequestException("Usuário não possui a role!");
                        });

        return "Role removida com sucesso";
    }
}
