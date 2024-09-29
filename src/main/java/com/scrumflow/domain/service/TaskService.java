package com.scrumflow.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scrumflow.application.dto.request.TaskRequestDTO;
import com.scrumflow.application.dto.response.TaskResponseDTO;
import com.scrumflow.domain.mapper.TaskMapper;
import com.scrumflow.domain.model.Task;
import com.scrumflow.domain.service.utilities.FeatureUtilities;
import com.scrumflow.domain.service.utilities.TaskUtilities;
import com.scrumflow.infrastructure.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskUtilities taskUtilities;
    private final FeatureUtilities featureUtilities;
    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.dtoToEntity(taskRequestDTO);
        taskUtilities.validateTaskFields(task, taskRequestDTO);
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
        Task task = taskUtilities.getTask(taskId);
        taskUtilities.validateTaskFields(task, taskRequestDTO);
        taskMapper.atualizaDeDto(taskRequestDTO, task);
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        Task task = taskUtilities.getTask(taskId);
        taskRepository.delete(task);
    }
}
