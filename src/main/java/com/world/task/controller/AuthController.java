package com.world.task.controller;

import com.world.task.util.JwtUtil;
import com.world.task.dto.auth.LoginRequestDTO;
import com.world.task.dto.response.ResponseDTO;
import com.world.task.enums.CommonEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginRequestDTO request) {

        if (!"admin".equals(request.getUsername()) ||
                !"admin123".equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = JwtUtil.generateToken(request.getUsername());

        return ResponseDTO.getResponseDto(
                CommonEnum.SUCCESS,
                "200",
                "Login successful",
                token,
                LocalDateTime.now()
        );
    }
}
