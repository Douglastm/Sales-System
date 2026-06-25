package com.douglas.salessystem.features.sale.dto;

import com.douglas.salessystem.features.saleitem.dto.SaleItemRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record SaleRequestDTO(

        @NotNull
        UUID customerId,

        @NotNull
        UUID sellerId,

        @NotNull
        UUID paymentMethodId,

        @NotEmpty
        List<@Valid SaleItemRequestDTO> items

) {
}
