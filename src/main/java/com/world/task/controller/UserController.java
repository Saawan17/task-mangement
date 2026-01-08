package com.world.task.controller;

import com.world.task.dto.PagedResponseDTO;
import com.world.task.dto.UserDTO;
import com.world.task.dto.ResponseDTO;
import com.world.task.enums.CommonEnum;
import com.world.task.model.UserModel;
import com.world.task.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String getRequest() {
        return "Hello World";
    }

    @PostMapping("/create/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO createUser(@Valid @RequestBody UserDTO request) {

        logger.info("Create User API called");

        UserModel userModel = userService.createUser(request);

        logger.info("User created successfully with id {}", userModel.getUserId());

        return ResponseDTO.getResponseDto(
                CommonEnum.SUCCESS,
                "201",
                "User created successfully",
                null,
                LocalDateTime.now()
        );
    }

    @GetMapping("/get/all/users")
    public ResponseDTO listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Fetching users list - page {}, size {}", page, size);

        // Manual validation
        if (page < 0) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 0");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }

        Pageable pageable = PageRequest.of(page, size);
        PagedResponseDTO<UserDTO> usersPage = userService.getAllUsers(pageable);

        return ResponseDTO.getResponseDto(
                CommonEnum.SUCCESS,
                "200",
                "Users fetched successfully",
                usersPage,
                LocalDateTime.now()
        );
    }

    @GetMapping("/get/{userId}")
    public ResponseDTO getUserById(@PathVariable("userId") String userId) {

        logger.info("Fetching user with id {}", userId);

        UserDTO user = userService.getUserById(userId);

        return ResponseDTO.getResponseDto(
                CommonEnum.SUCCESS,
                "200",
                "User fetched successfully",
                user,
                LocalDateTime.now()
        );
    }

    @DeleteMapping("/delete/user/{userId}")
    public ResponseDTO deleteUser(@PathVariable("userId") String userId) {

        logger.info("Fetching user with id {}", userId);

        userService.deleteUser(userId);

        return ResponseDTO.getResponseDto(
                CommonEnum.SUCCESS,
                "200",
                "User deleted successfully",
                null,
                LocalDateTime.now()
        );

    }
}

