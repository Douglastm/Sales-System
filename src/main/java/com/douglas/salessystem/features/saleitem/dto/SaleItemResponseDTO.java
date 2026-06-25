package com.douglas.salessystem.features.saleitem.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItemResponseDTO(

        UUID id,

        UUID productId,

        String productName,

        Integer quantity,

        BigDecimal unitPrice,

        BigDecimal subtotal

) {
}
