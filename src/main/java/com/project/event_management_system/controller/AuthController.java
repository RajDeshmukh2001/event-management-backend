package com.project.event_management_system.controller;

import com.project.event_management_system.dto.CreateUserRequest;
import com.project.event_management_system.dto.CreateUserResponse;
import com.project.event_management_system.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse create(@RequestBody CreateUserRequest request) {
        return authService.create(request);
    }
}
