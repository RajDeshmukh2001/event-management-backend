package com.project.event_management_system.dto;

import com.project.event_management_system.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponse {
    private String token;
    private UUID id;
    private String email;
    private UserRole role;
}
