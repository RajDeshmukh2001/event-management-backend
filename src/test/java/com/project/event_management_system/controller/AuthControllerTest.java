package com.project.event_management_system.controller;

import com.project.event_management_system.enums.UserRole;
import com.project.event_management_system.model.User;
import com.project.event_management_system.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private User userRequest;

    private User getUserRequest() {
        userRequest = new User();
        userRequest.setId(UUID.randomUUID());
        userRequest.setName("Raj");
        userRequest.setEmail("raj@gmail.com");
        userRequest.setPassword("R@j2001");
        userRequest.setRole(UserRole.ATTENDEE);
        userRequest.setCreatedAt(LocalDateTime.now());
        userRequest.setUpdatedAt(LocalDateTime.now());

        return userRequest;
    }

    @BeforeEach
    void setUp() {
        user = getUserRequest();
    }

    @Test
    void shouldReturn201AndUser_WhenUserCreatedSuccessfully() throws Exception {
        // When
        when(authService.create(any(User.class))).thenReturn(user);

        // Given and Then
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Raj"))
                .andExpect(jsonPath("$.email").value("raj@gmail.com"))
                .andExpect(jsonPath("$.password").value("R@j2001"))
                .andExpect(jsonPath("$.role").value("ATTENDEE"));
    }
}