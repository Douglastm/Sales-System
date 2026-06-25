package com.douglas.salessystem.features.sale.mapper;

import com.douglas.salessystem.features.sale.dto.SaleResponseDTO;
import com.douglas.salessystem.features.sale.model.Sale;
import com.douglas.salessystem.features.saleitem.mapper.SaleItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleMapper {

    private final SaleItemMapper saleItemMapper;

    public SaleResponseDTO toResponse(Sale sale) {

        return new SaleResponseDTO(

                sale.getId(),

                sale.getCustomer().getId(),
                sale.getCustomer().getName(),

                sale.getSeller().getId(),
                sale.getSeller().getName(),

                sale.getPaymentMethod().getId(),
                sale.getPaymentMethod().getName(),

                sale.getSaleDate(),

                sale.getTotalAmount(),

                sale.getStatus(),

                sale.getItems()
                        .stream()
                        .map(saleItemMapper::toResponse)
                        .toList()

        );
    }

}
