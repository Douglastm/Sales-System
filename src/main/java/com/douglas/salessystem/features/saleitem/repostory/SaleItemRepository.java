package com.douglas.salessystem.features.saleitem.repostory;

import com.douglas.salessystem.features.saleitem.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleItemRepository
        extends JpaRepository<SaleItem, UUID> {
}
