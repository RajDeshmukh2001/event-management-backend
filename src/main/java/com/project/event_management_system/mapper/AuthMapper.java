package com.project.event_management_system.mapper;

import com.project.event_management_system.dto.CreateUserRequest;
import com.project.event_management_system.dto.CreateUserResponse;
import com.project.event_management_system.dto.LoginResponse;
import com.project.event_management_system.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name")
    @Mapping(target = "email")
    User toEntity(CreateUserRequest request);

    CreateUserResponse toResponseDTO(User user);

    @Mapping(target = "token", ignore = true)
    LoginResponse toLoginResponseDTO(User user);
}
