package com.project.event_management_system.service;

import com.project.event_management_system.enums.UserRole;
import com.project.event_management_system.model.User;
import com.project.event_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Raj");
        user.setEmail("raj@gmail.com");
        user.setPassword("R@j2001");
        user.setRole(UserRole.ATTENDEE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Given
        when(userRepository.save(user)).thenReturn(user);

        // When
        User newUser = authService.create(user);

        // Then
        assertNotNull(newUser);
        assertEquals("Raj", newUser.getName());
        assertEquals("raj@gmail.com", newUser.getEmail());
        assertEquals("R@j2001", newUser.getPassword());
        assertEquals("ATTENDEE", newUser.getRole().name());
    }
}