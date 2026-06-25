package com.douglas.salessystem.features.saleitem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SaleItemRequestDTO(

        @NotNull
        UUID productId,

        @NotNull
        @Min(1)
        Integer quantity

) {
}