package com.douglas.salessystem.features.address.dto;

import java.util.UUID;

public record AddressResponseDTO(

        UUID id,
        String zipCode,
        String street,
        String number,
        String complement,
        String district,
        String city,
        String state

) {
}
