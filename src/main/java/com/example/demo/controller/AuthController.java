package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());

        if (user == null) {
            return new LoginResponse(null, null, "Invalid username or password");
        }

        return new LoginResponse(user.getId(), user.getUsername(), "Login successful");
    }
}