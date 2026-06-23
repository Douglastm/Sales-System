package com.douglas.salessystem.features.user.mapper;

import com.douglas.salessystem.features.user.dto.UserRequestDTO;
import com.douglas.salessystem.features.user.dto.UserResponseDTO;
import com.douglas.salessystem.features.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {

        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .role(dto.role())
                .active(true)
                .build();
    }

    public UserResponseDTO toResponse(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getActive()
        );
    }
}
