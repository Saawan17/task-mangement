package com.world.task.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    private String userId;

    @NotBlank(message = "Name is required")
    @Size(min = 2)
    private String name;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    public UserDTO() {
        super();
    }

    public void setUserId(String userId) { this.userId = userId; }

    public String getUserId(){ return userId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
