package com.douglas.salessystem.features.saleitem.mapper;

import com.douglas.salessystem.features.saleitem.dto.SaleItemResponseDTO;
import com.douglas.salessystem.features.saleitem.model.SaleItem;
import org.springframework.stereotype.Component;

@Component
public class SaleItemMapper {

    public SaleItemResponseDTO toResponse(SaleItem item){

        return new SaleItemResponseDTO(

                item.getId(),

                item.getProduct().getId(),

                item.getProduct().getName(),

                item.getQuantity(),

                item.getUnitPrice(),

                item.getSubtotal()

        );

    }

}
