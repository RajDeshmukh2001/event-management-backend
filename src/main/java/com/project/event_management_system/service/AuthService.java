package com.project.event_management_system.service;

import com.project.event_management_system.model.User;
import com.project.event_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }
}
