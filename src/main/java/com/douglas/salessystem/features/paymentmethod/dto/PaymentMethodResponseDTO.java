package com.douglas.salessystem.features.paymentmethod.dto;

import java.util.UUID;

public record PaymentMethodResponseDTO(

        UUID id,
        String name,
        String description,
        Boolean active

) {
}
