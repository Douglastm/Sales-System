package com.douglas.salessystem.features.sale.repository;

import com.douglas.salessystem.features.sale.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleRepository
        extends JpaRepository<Sale, UUID> {
}
