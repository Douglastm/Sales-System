package com.douglas.salessystem.features.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(

        @NotBlank(message = "Name is required")
        String name,

        String description

) {
}
