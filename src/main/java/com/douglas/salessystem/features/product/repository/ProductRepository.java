package com.douglas.salessystem.features.product.repository;

import com.douglas.salessystem.features.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository
        extends JpaRepository<Product, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
