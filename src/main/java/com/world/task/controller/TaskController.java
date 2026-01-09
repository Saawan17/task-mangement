package com.world.task.controller;

import com.world.task.dto.response.ResponseDTO;
import com.world.task.dto.task.TaskDTO;
import com.world.task.dto.task.UpdateTaskStatusDTO;
import com.world.task.enums.CommonEnum;
import com.world.task.model.TaskModel;
import com.world.task.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create/task")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO createTask(@Valid @RequestBody TaskDTO request) {

        logger.info("Create Task API called");

        TaskModel task = taskService.createTask(request);

        logger.info("Task created successfully with id {}", task.getTaskId());

        return ResponseDTO.getResponseDto(
                CommonEnum.SUCCESS,
                "201",
                "Task created successfully",
                null,
                LocalDateTime.now()
        );
    }

    @GetMapping("/get/filter/{status}/{priority}/{userId}")
    public ResponseDTO filterTasks(
            @PathVariable("status") String status,
            @PathVariable("priority") String priority,
            @PathVariable("userId") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }

        Pageable pageable = PageRequest.of(page, size);

        return ResponseDTO.getResponseDto(
                CommonEnum.SUCCESS,
                "200",
                "Tasks fetched successfully",
                taskService.filterTasks(status, priority, userId, pageable),
                LocalDateTime.now()
        );
    }

    @GetMapping("/get/{taskId}")
    public ResponseDTO getTaskById(@PathVariable("taskId") String taskId) {

        TaskDTO task = taskService.getTaskById(taskId);

        return ResponseDTO.getResponseDto(
                CommonEnum.SUCCESS,
                "200",
                "Task fetched successfully",
                task,
                LocalDateTime.now()
        );
    }

    @PutMapping("/update/{taskId}")
    public ResponseDTO updateTask(
            @PathVariable("taskId") String taskId,
            @RequestBody TaskDTO request) {

        taskService.updateTask(taskId, request);

        return ResponseDTO.getResponseDto(
                CommonEnum.UPDATED,
                "200",
                "Task updated successfully",
                null,
                LocalDateTime.now()
        );
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseDTO deleteTask(@PathVariable("taskId") String taskId) {

        taskService.deleteTask(taskId);

        return ResponseDTO.getResponseDto(
                CommonEnum.DELETED,
                "200",
                "Task deleted successfully",
                null,
                LocalDateTime.now()
        );
    }

    @PatchMapping("/{taskId}/status")
    public ResponseDTO updateTaskStatus(
            @PathVariable("taskId") String taskId,
            @RequestBody UpdateTaskStatusDTO request) {

        taskService.updateTaskStatus(taskId, request.getStatus());

        return ResponseDTO.getResponseDto(
                CommonEnum.UPDATED,
                "200",
                "Task status updated successfully",
                null,
                LocalDateTime.now()
        );
    }

}
