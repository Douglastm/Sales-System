package com.douglas.salessystem.features.customer.dto;

import com.douglas.salessystem.features.address.dto.AddressRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(

        @NotBlank
        String name,

        String cpf,

        @Email
        String email,

        String phone,

        @Valid
        AddressRequestDTO address

) {
}
