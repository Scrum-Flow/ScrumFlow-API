package com.scrumflow.domain.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.LoginRequestDTO;
import com.scrumflow.application.dto.request.RegisterRequestDTO;
import com.scrumflow.application.dto.response.LoginResponseDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.application.dto.response.RoleResponseDTO;
import com.scrumflow.application.dto.response.UserResponseDTO;
import com.scrumflow.domain.enums.RoleType;
import com.scrumflow.domain.exception.BadRequestException;
import com.scrumflow.domain.exception.InvalidCredentialsException;
import com.scrumflow.domain.mapper.ProjectMapper;
import com.scrumflow.domain.mapper.UserMapper;
import com.scrumflow.domain.model.Role;
import com.scrumflow.domain.model.Team;
import com.scrumflow.domain.model.User;
import com.scrumflow.domain.service.utilities.UserUtilities;
import com.scrumflow.infrastructure.config.security.TokenService;
import com.scrumflow.infrastructure.repository.RoleRepository;
import com.scrumflow.infrastructure.repository.UserRepository;
import com.scrumflow.infrastructure.utilities.RegisterValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final UserUtilities userUtilities;
    private final RoleRepository roleRepository;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

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

        User newUser = new User();
        newUser.setName(registerRequestDTO.name());
        newUser.setEmail(registerRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        newUser.getRoles().add(roleRepository.findByName(RoleType.USER).get());

        userRepository.save(newUser);

        return new LoginResponseDTO(
                userMapper.entityToDto(newUser), tokenService.generateToken(newUser));
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<User> user = userRepository.findByEmail(loginRequestDTO.email());

        return user.filter(u -> passwordEncoder.matches(loginRequestDTO.password(), u.getPassword()))
                .map(u -> new LoginResponseDTO(userMapper.entityToDto(u), tokenService.generateToken(u)))
                .orElseThrow(() -> new InvalidCredentialsException("Usuário ou senha inválidos"));
    }

    public UserResponseDTO updateUser(Long userId, List<Long> roleIds) {
        User u = userUtilities.getUserById(userId);

        u.setRoles(
                roleIds.stream()
                        .map(
                                roleId ->
                                        roleRepository
                                                .findById(roleId)
                                                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId)))
                        .collect(Collectors.toList()));

        return userMapper.entityToDto(userRepository.save(u));
    }

    @Deprecated
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

    @Deprecated
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

    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream().map(userMapper::entityToDto).toList();
    }

    public List<ProjectResponseDTO> findUserProjects(Long userId) {
        User user = userUtilities.getUserById(userId);

        return user.getTeams().stream().map(Team::getProject).map(projectMapper::entityToDto).toList();
    }

    public List<RoleResponseDTO> getRoles() {
        return roleRepository.findAll().stream()
                .map(r -> new RoleResponseDTO(r.getId(), r.getName().name()))
                .toList();
    }
}
