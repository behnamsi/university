package com.behnam.university.response;

import org.springframework.http.HttpStatus;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/17/2022
 */

public class ResponseModel {
    private String message;
    private HttpStatus status;
    private Object data;

    public ResponseModel() {
    }

    public ResponseModel(String message, HttpStatus status, Object responseObject) {
        this.message = message;
        this.status = status;
        this.data = responseObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", responseObject=" + data +
                '}';
    }
}
