package com.douglas.salessystem.features.product.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequestDTO(

        @NotBlank
        String name,

        String description,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer stockQuantity,

        @NotNull
        UUID categoryId

) {
}