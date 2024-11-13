package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.TaskRequestDTO;
import com.scrumflow.application.dto.response.TaskResponseDTO;
import com.scrumflow.domain.dto.EmailDTO;
import com.scrumflow.domain.enums.TaskStatus;
import com.scrumflow.domain.mapper.TaskMapper;
import com.scrumflow.domain.model.Task;
import com.scrumflow.domain.service.utilities.FeatureUtilities;
import com.scrumflow.domain.service.utilities.TaskUtilities;
import com.scrumflow.domain.service.utilities.UserUtilities;
import com.scrumflow.infrastructure.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskUtilities taskUtilities;
    private final FeatureUtilities featureUtilities;
    private final EmailService emailService;
    private final UserUtilities userUtilities;
    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.dtoToEntity(taskRequestDTO);
        taskUtilities.validateTaskFields(task, taskRequestDTO);
        task.setStatus(TaskStatus.NOT_STARTED);
        task = taskRepository.save(task);
        return taskMapper.entityToDto(task);
    }

    public List<TaskResponseDTO> findTasksByFeature(Long featureId) {
        featureUtilities.exists(featureId);
        return taskRepository.findAllByFeatureId(featureId).stream()
                .map(taskMapper::entityToDto)
                .toList();
    }

    public TaskResponseDTO findTaskById(Long taskId) {
        Task task = taskUtilities.getTask(taskId);
        return taskMapper.entityToDto(task);
    }

    public void updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        var task = taskUtilities.getTask(taskId);
        taskUtilities.validateTaskFields(task, taskRequestDTO);

        var status = task.getStatus();

        taskMapper.atualizaDeDto(taskRequestDTO, task);
        if (!status.equals(taskRequestDTO.status())) {
            emailService.sendEmail(
                    new EmailDTO(
                            status,
                            task.getStatus(),
                            userUtilities.getUserById(task.getAssignedTo().getId()),
                            task.getName()));
        }

        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        Task task = taskUtilities.getTask(taskId);
        taskRepository.delete(task);
    }
}
