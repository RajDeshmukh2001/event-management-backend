package com.project.event_management_system.service;

import com.project.event_management_system.dto.CreateUserRequest;
import com.project.event_management_system.dto.CreateUserResponse;
import com.project.event_management_system.dto.LoginRequest;
import com.project.event_management_system.enums.UserRole;
import com.project.event_management_system.exception.EmailAlreadyExistsException;
import com.project.event_management_system.exception.ResourceNotFoundException;
import com.project.event_management_system.mapper.UserMapper;
import com.project.event_management_system.model.User;
import com.project.event_management_system.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    public CreateUserResponse create(CreateUserRequest request) {
        String userName = request.getName().trim();
        String userEmail = request.getEmail().toLowerCase().trim();

        if (userRepository.findByEmail(userEmail).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        request.setName(userName);
        request.setEmail(userEmail);
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ATTENDEE);
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    public String login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User with email does not exists"));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        }

        return "Fail";
    }
}
