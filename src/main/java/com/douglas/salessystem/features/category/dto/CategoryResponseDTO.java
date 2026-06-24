package com.douglas.salessystem.features.category.dto;

import java.util.UUID;

public record CategoryResponseDTO(

        UUID id,
        String name,
        String description,
        Boolean active

) {
}
