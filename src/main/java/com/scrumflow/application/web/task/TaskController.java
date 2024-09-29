package com.scrumflow.application.web.task;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.scrumflow.application.dto.request.TaskRequestDTO;
import com.scrumflow.application.dto.response.TaskResponseDTO;
import com.scrumflow.domain.service.TaskService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController implements TaskApi {

    private final TaskService taskService;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        return taskService.createTask(taskRequestDTO);
    }

    @Override
    public List<TaskResponseDTO> findTasksByFeature(Long featureId) {
        return taskService.findTasksByFeature(featureId);
    }

    @Override
    public TaskResponseDTO findTaskById(Long taskId) {
        return taskService.findTaskById(taskId);
    }

    @Override
    public void updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        taskService.updateTask(taskId, taskRequestDTO);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskService.deleteTask(taskId);
    }
}
