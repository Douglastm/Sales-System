package com.douglas.salessystem.features.address.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(

        @NotBlank
        String zipCode,

        @NotBlank
        String street,

        String number,

        String complement,

        @NotBlank
        String district,

        @NotBlank
        String city,

        @NotBlank
        String state

) {
}
