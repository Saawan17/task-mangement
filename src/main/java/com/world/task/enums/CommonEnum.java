package com.world.task.enums;

import com.world.task.dto.response.ResponseInterface;

public enum CommonEnum implements ResponseInterface {

    ALF("Admin Login in Failed"),
    SUCCESS("Operation SUCCESS"),
    DELETED("Delete Successfully"),
    UPDATED("Updated Successfully"),
    INVALID("Invalid Response"),
    NULL("null"),
    FAILURE("Failed");

    private final String response;

    // Constructor
    CommonEnum(String response) {
        this.response = response;
    }

    @Override
    public String getMethodResponce() {
        return response;
    }

}

