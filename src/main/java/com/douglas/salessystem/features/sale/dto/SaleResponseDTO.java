package com.douglas.salessystem.features.sale.dto;

import com.douglas.salessystem.shared.enums.SaleStatus;
import com.douglas.salessystem.features.saleitem.dto.SaleItemResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SaleResponseDTO(

        UUID id,

        UUID customerId,
        String customerName,

        UUID sellerId,
        String sellerName,

        UUID paymentMethodId,
        String paymentMethod,

        LocalDateTime saleDate,

        BigDecimal totalAmount,

        SaleStatus status,

        List<SaleItemResponseDTO> items

) {
}
