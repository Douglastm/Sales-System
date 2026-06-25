package com.douglas.salessystem.features.paymentmethod.dto;

import jakarta.validation.constraints.NotBlank;

public record PaymentMethodRequestDTO(

        @NotBlank
        String name,

        String description

) {
}
