package com.douglas.salessystem.features.product.mapper;

import com.douglas.salessystem.features.product.dto.ProductResponseDTO;
import com.douglas.salessystem.features.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponse(Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),

                product.getCategory().getId(),
                product.getCategory().getName(),

                product.getActive()
        );
    }
}