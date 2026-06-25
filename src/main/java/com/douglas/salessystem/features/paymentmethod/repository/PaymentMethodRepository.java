package com.douglas.salessystem.features.paymentmethod.repository;

import com.douglas.salessystem.features.paymentmethod.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentMethodRepository
        extends JpaRepository<PaymentMethod, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
