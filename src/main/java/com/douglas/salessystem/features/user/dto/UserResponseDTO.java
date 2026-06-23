package com.douglas.salessystem.features.user.dto;

import com.douglas.salessystem.shared.enums.Role;

import java.util.UUID;

public record UserResponseDTO(

        UUID id,
        String name,
        String email,
        Role role,
        Boolean active

) {
}
