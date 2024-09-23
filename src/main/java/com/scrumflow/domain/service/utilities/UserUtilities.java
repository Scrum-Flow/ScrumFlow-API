package com.scrumflow.domain.service.utilities;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scrumflow.application.dto.response.RoleDTO;
import com.scrumflow.domain.enums.RoleType;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.model.Role;
import com.scrumflow.domain.model.User;
import com.scrumflow.infrastructure.repository.RoleRepository;
import com.scrumflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserUtilities {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Não foi possível encontrar o usuário com id: %s", id));
    }

    public Role getRoleById(Long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Não foi possível encontrar a role de id: %s", id));
    }

    public Role getRoleByName(RoleType name) {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException("Não foi possível encontrar a role " + name));
    }

    public List<RoleDTO> getUserRoles(User user) {
        return user.getRoles().stream().map(r -> new RoleDTO(r.getName().name())).toList();
    }
}
