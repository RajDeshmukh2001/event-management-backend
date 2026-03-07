package com.project.event_management_system.service;

import com.project.event_management_system.dto.CreateUserRequest;
import com.project.event_management_system.dto.CreateUserResponse;
import com.project.event_management_system.enums.UserRole;
import com.project.event_management_system.model.User;
import com.project.event_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

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
                .password("R@j2001")
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
    void shouldCreateUserSuccessfully() {
        // Given
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        CreateUserResponse response = authService.create(userRequest);

        // Then
        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals("Raj", response.getName());
        assertEquals("raj@gmail.com", response.getEmail());
        assertEquals("ATTENDEE", response.getRole().name());
        assertEquals(user.getCreatedAt(), response.getCreatedAt());
    }
}