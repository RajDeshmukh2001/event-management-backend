package com.project.event_management_system.service;

import com.project.event_management_system.dto.CreateUserRequest;
import com.project.event_management_system.dto.CreateUserResponse;
import com.project.event_management_system.enums.UserRole;
import com.project.event_management_system.exception.EmailAlreadyExistsException;
import com.project.event_management_system.mapper.UserMapper;
import com.project.event_management_system.model.User;
import com.project.event_management_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public CreateUserResponse create(CreateUserRequest request) {
        String userEmail = request.getEmail().toLowerCase().trim();
        if (userRepository.findByEmail(userEmail).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ATTENDEE);
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }
}
