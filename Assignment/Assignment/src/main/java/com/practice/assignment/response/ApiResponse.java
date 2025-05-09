package com.practice.assignment.response;

import java.util.List;

public class ApiResponse<T> {
    private String status;
    private String message;
    private List<T> data;

    public ApiResponse() {
    }

    public ApiResponse(String status, String message, List<T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
