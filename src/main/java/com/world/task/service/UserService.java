package com.world.task.service;

import com.world.task.dto.response.PagedResponseDTO;
import com.world.task.dto.user.UserDTO;
import com.world.task.exception.DuplicateResourceException;
import com.world.task.exception.ResourceNotFoundException;
import com.world.task.model.UserModel;
import com.world.task.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserDTO request) {
        // Check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        UserModel userModel = new UserModel();
        userModel.setEmail(request.getEmail());
        userModel.setName(request.getName());
        return userRepository.save(userModel);
    }

    public PagedResponseDTO<UserDTO> getAllUsers(Pageable pageable) {

        Page<UserDTO> userPage = userRepository.findAll(pageable)
                .map(model -> {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(model.getUserId());
                    dto.setName(model.getName());
                    dto.setEmail(model.getEmail());
                    return dto;
                });

        PagedResponseDTO<UserDTO> response = new PagedResponseDTO<>();
        response.setContent(userPage.getContent());
        response.setPage(userPage.getNumber());
        response.setSize(userPage.getSize());
        response.setTotalElements(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());

        return response;
    }

    public UserDTO getUserById(String userId) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with userId " + userId));

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public void deleteUser(String userId) {

        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with userId " + userId));

        userRepository.delete(userModel);
    }
}
