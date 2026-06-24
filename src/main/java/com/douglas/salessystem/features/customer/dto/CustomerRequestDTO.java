package com.douglas.salessystem.features.customer.dto;

import com.douglas.salessystem.features.address.dto.AddressRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequestDTO(

        @NotBlank
        String name,

        @CPF(message = "Invalid CPF")
        String cpf,

        @Email
        String email,

        String phone,

        @Valid
        AddressRequestDTO address

) {
}
