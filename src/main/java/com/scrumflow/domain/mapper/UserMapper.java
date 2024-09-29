package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.response.UserResponseDTO;
import com.scrumflow.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO entityToDto(User user);
}
