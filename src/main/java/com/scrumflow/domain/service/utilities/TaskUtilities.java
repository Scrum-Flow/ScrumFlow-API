package com.scrumflow.domain.service.utilities;

import org.springframework.stereotype.Component;

import com.scrumflow.application.dto.request.TaskRequestDTO;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;
import com.scrumflow.domain.model.Feature;
import com.scrumflow.domain.model.Task;
import com.scrumflow.domain.model.User;
import com.scrumflow.infrastructure.repository.TaskRepository;
import com.scrumflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskUtilities {

    private final TaskRepository taskRepository;
    private final FeatureUtilities featureUtilities;
    private final UserRepository userRepository;

    public Task getTask(Long taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        String.format("Nenhuma task encontrada para o id: %s", taskId)));
    }

    public void validateTaskFields(Task task, TaskRequestDTO taskRequestDTO) {
        Feature feature = featureUtilities.getFeature(taskRequestDTO.featureId());

        if (taskRequestDTO.assignedToUserId() != null) {
            User user =
                    userRepository
                            .findById(taskRequestDTO.assignedToUserId())
                            .orElseThrow(
                                    () ->
                                            new NotFoundException(
                                                    String.format(
                                                            "Nenhum usuário encontrado com o id: %s",
                                                            taskRequestDTO.assignedToUserId())));

            if (!user.isActive()) {
                throw new BusinessException("Não é permitido atribuir uma tarefa a um usuário inativo.");
            }

            task.setAssignedTo(user);
        }

        task.setFeature(feature);
    }
}
