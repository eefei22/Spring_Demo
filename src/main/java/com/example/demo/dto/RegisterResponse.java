package com.example.demo.dto;

public class RegisterResponse {

    private Long userId;
    private String username;
    private String message;

    public RegisterResponse() {
    }

    public RegisterResponse(Long userId, String username, String message) {
        this.userId = userId;
        this.username = username;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}