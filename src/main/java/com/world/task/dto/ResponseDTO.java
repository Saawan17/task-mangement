package com.world.task.dto;

import com.world.task.enums.CommonEnum;

import java.time.LocalDateTime;

public class ResponseDTO {

    private ResponseInterface responseId;
    private String response;
    private String status;
    private String message;
    private Object data;
    private LocalDateTime datetime;

    public static ResponseDTO getResponseDto(String status, String message, Object data) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(status);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public ResponseDTO() {
        this.responseId = CommonEnum.INVALID;
        this.response = responseId.getMethodResponce();
    }

    public ResponseDTO(ResponseInterface responseId) {
        this.responseId = responseId;
        this.response = responseId.getMethodResponce();

    }

    public ResponseDTO(ResponseInterface responseId, String response) {
        this.responseId = responseId;
        this.response = response;
    }

    public static ResponseDTO getResponseDto(ResponseInterface responseId) {
        return new ResponseDTO(responseId);
    }

    public static ResponseDTO getResponseDto(ResponseInterface responseId, String response) {
        return new ResponseDTO(responseId, response);
    }

    public static ResponseDTO getResponseDto(ResponseInterface responseId, Object data) {
        ResponseDTO responseDto = new ResponseDTO(responseId);
        responseDto.setData(data); // Set the data object
        return responseDto;
    }

    public static ResponseDTO getResponseDto(ResponseInterface responseId, String message, Object data) {
        ResponseDTO responseDto = new ResponseDTO(responseId);
        responseDto.setMessage(message); // Set the message
        responseDto.setData(data); // Set the data object
        return responseDto;
    }

    public static ResponseDTO getResponseDto(ResponseInterface responseId, String status, String message, Object data, LocalDateTime dateTime) {
        ResponseDTO responseDto = new ResponseDTO(responseId);
        responseDto.setMessage(message); // Set the message
        responseDto.setStatus(status);
        responseDto.setData(data);// Set the status
        responseDto.setDatetime(dateTime);
        return responseDto;
    }


    public ResponseInterface getResponseId() {
        return responseId;
    }

    public void setResponseId(ResponseInterface responseId) {
        this.responseId = responseId;
    }

    public void setResponseString(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setResponce(String response) {
        this.response = response;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
