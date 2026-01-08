package com.world.task.enums;

import com.world.task.dto.response.ResponseInterface;

public enum CommonEnum implements ResponseInterface {

    ULF("User Login in Failed"),
    SUCCESS("Operation SUCCESS"),
    DELETED("Delete Successfully"),
    UPDATED("Updated Successfully"),
    INVALID("Invalid Responce"),
    NULL("null"),
    EXISTS("Alreay exists"),
    FAILURE("Failed"),
    NOT_FOUND("not found"),
    FORBIDDEN("Not Authorised"),
    IBOT("Invalid Business Owner Type"),
    OCS("Order Created Successfully"),
    EWCO("Error While Creating Order"),
    NUF("No User Found"),
    BR("Bad Request"),
    NO_DATA("No Data Found");

    private String response;

    // Constructor
    CommonEnum(String response) {
        this.response = response;
    }

    @Override
    public String getMethodResponce() {
        return response;
    }

}

