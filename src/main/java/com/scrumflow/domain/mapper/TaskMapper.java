package com.scrumflow.domain.mapper;

import com.scrumflow.application.dto.request.TaskRequestDTO;
import com.scrumflow.application.dto.response.TaskResponseDTO;
import com.scrumflow.domain.model.Task;
import com.scrumflow.domain.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "feature.name", target = "featureName")
    @Mapping(source = "assignedTo.name", target = "assignedToUserName", defaultValue = "null")
    TaskResponseDTO entityToDto(Task task);

    @Mapping(source = "assignedToUserId", target = "assignedTo", qualifiedByName = "mapAssignedTo")
    Task dtoToEntity(TaskRequestDTO taskRequestDTO);

    @Named("mapAssignedTo")
    default User mapAssignedTo(Long assignedToUserId) {
        if (assignedToUserId == null) {
            return null;
        }
        User user = new User();
        user.setId(assignedToUserId);
        return user;
    }

    @Mapping(source = "featureId", target = "feature.id")
    @Mapping(source = "assignedToUserId", target = "assignedTo", qualifiedByName = "mapAssignedTo")
    void atualizaDeDto(TaskRequestDTO taskRequestDTO, @MappingTarget Task task);
}
