package com.scrumflow.application.web.user;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.application.dto.response.UserResponseDTO;
import com.scrumflow.domain.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public List<UserResponseDTO> findAll() {
        return userService.findAllUsers();
    }

    @Override
    public List<ProjectResponseDTO> findUserProjects(Long userId) {
        return userService.findUserProjects(userId);
    }

    @Override
    public UserResponseDTO updateUser(Long userId, List<Long> roleIds) {
        return userService.updateUser(userId, roleIds);
    }
}
