package com.douglas.salessystem.features.customer.dto;

import com.douglas.salessystem.features.address.dto.AddressResponseDTO;

import java.util.UUID;

public record CustomerResponseDTO(

        UUID id,
        String name,
        String cpf,
        String email,
        String phone,
        AddressResponseDTO address,
        Boolean active

) {
}
