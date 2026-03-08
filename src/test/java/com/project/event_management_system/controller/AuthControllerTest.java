package com.project.event_management_system.controller;

import com.project.event_management_system.dto.CreateUserRequest;
import com.project.event_management_system.dto.CreateUserResponse;
import com.project.event_management_system.enums.UserRole;
import com.project.event_management_system.exception.EmailAlreadyExistsException;
import com.project.event_management_system.model.User;
import com.project.event_management_system.service.AuthService;
import com.project.event_management_system.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {
    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private CreateUserRequest userRequest;
    private CreateUserResponse userResponse;

    private User getUser() {
        user = User.builder()
                .name("Raj")
                .email("raj@gmail.com")
                .password("$2a$hashed")
                .updatedAt(LocalDateTime.now())
                .build();
        return user;
    }

    private CreateUserRequest getUserRequest() {
        userRequest = CreateUserRequest.builder()
                .name("Raj")
                .email("raj@gmail.com")
                .password("R@jdd2001")
                .build();
        return userRequest;
    }

    private CreateUserResponse getUserResponse() {
        userResponse = CreateUserResponse.builder()
                .name("Raj")
                .email("raj@gmail.com")
                .build();
        return userResponse;
    }

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        UserRole role = UserRole.ATTENDEE;
        LocalDateTime createdAt = LocalDateTime.now();

        user = getUser();
        user.setId(id);
        user.setRole(role);
        user.setCreatedAt(createdAt);

        userRequest = getUserRequest();

        userResponse = getUserResponse();
        userResponse.setId(id);
        userResponse.setRole(role);
        userResponse.setCreatedAt(createdAt);
    }

    @Test
    void shouldReturn201AndUser_WhenUserCreatedSuccessfully() throws Exception {
        // When
        when(authService.create(any(CreateUserRequest.class))).thenReturn(userResponse);

        // Given and Then
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.name").value(userResponse.getName()))
                .andExpect(jsonPath("$.data.email").value(userResponse.getEmail()))
                .andExpect(jsonPath("$.data.role").value(userResponse.getRole().name()))
                .andExpect(jsonPath("$.data.createdAt").value(userResponse.getCreatedAt().toString()));
    }

    @Test
    void shouldReturn409AndThrowException_WhenUserEmailAlreadyExists() throws Exception {
        // When
        when(authService.create(any(CreateUserRequest.class))).thenThrow(new EmailAlreadyExistsException("Email already exists"));

        // When and Then
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("EMAIL_EXISTS"))
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }
}