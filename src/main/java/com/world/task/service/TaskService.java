package com.world.task.service;

import com.world.task.dto.response.PagedResponseDTO;
import com.world.task.dto.task.TaskDTO;
import com.world.task.enums.TaskPriority;
import com.world.task.enums.TaskStatus;
import com.world.task.exception.ResourceNotFoundException;
import com.world.task.model.TaskModel;
import com.world.task.model.UserModel;
import com.world.task.repository.TaskRepository;
import com.world.task.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskModel createTask(TaskDTO request) {

        TaskModel task = new TaskModel();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        try {
            task.setStatus(TaskStatus.valueOf(request.getStatus()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid task status");
        }

        try {
            task.setPriority(TaskPriority.valueOf(request.getPriority()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid task priority");
        }

        if (request.getAssignedToUserId() != null) {
            UserModel user = userRepository.findById(request.getAssignedToUserId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found with id " + request.getAssignedToUserId()
                            )
                    );
            task.setAssignedTo(user);
        }
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public PagedResponseDTO<TaskDTO> filterTasks(
            String status,
            String priority,
            String userId,
            Pageable pageable) {

        TaskStatus taskStatus = null;
        TaskPriority taskPriority = null;
        String assignedUserId = null;

        // Status filter
        if (!"ALL".equalsIgnoreCase(status)) {
            try {
                taskStatus = TaskStatus.valueOf(status.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid task status");
            }
        }

        // Priority filter
        if (!"ALL".equalsIgnoreCase(priority)) {
            try {
                taskPriority = TaskPriority.valueOf(priority.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid task priority");
            }
        }

        // User filter
        if (userId != null) {
            assignedUserId = userId;
        }

        Page<TaskModel> page = taskRepository.filterTasks(
                taskStatus,
                taskPriority,
                assignedUserId,
                pageable
        );

        // Map to DTO
        List<TaskDTO> tasks = page.getContent().stream().map(task -> {
            TaskDTO dto = new TaskDTO();
            dto.setTaskId(task.getTaskId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus().name());
            dto.setPriority(task.getPriority().name());
            if (task.getDueDate() != null)
                dto.setDueDate(task.getDueDate());
            if (task.getAssignedTo() != null)
                dto.setAssignedToUserId(task.getAssignedTo().getUserId());
            return dto;
        }).toList();

        PagedResponseDTO<TaskDTO> response = new PagedResponseDTO<>();
        response.setContent(tasks);
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());

        return response;
    }


    public TaskDTO getTaskById(String taskId) {

        TaskModel task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id " + taskId)
                );

        TaskDTO dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus().name());
        dto.setPriority(task.getPriority().name());
        dto.setDueDate(task.getDueDate());

        if (task.getAssignedTo() != null) {
            dto.setAssignedToUserId(task.getAssignedTo().getUserId());
        }

        return dto;
    }

    public void updateTask(String taskId, TaskDTO request) {

        TaskModel task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id " + taskId)
                );

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            try {
                task.setStatus(TaskStatus.valueOf(request.getStatus()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid task status");
            }
        }

        if (request.getPriority() != null) {
            try {
                task.setPriority(TaskPriority.valueOf(request.getPriority()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid task priority");
            }
        }

        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        if (request.getAssignedToUserId() != null) {
            UserModel user = userRepository.findById(request.getAssignedToUserId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found with id " + request.getAssignedToUserId()
                            )
                    );
            task.setAssignedTo(user);
        }

        taskRepository.save(task);
    }

    public void deleteTask(String taskId) {

        TaskModel task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id " + taskId)
                );

        taskRepository.delete(task);
    }

    public void updateTaskStatus(String taskId, String status) {

        TaskModel task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id " + taskId)
                );

        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }

        try {
            task.setStatus(TaskStatus.valueOf(status));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid task status");
        }

        taskRepository.save(task);
    }


}

