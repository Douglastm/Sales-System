package com.douglas.salessystem.features.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(

        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,

        UUID categoryId,
        String categoryName,

        Boolean active

) {
}
