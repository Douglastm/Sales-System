package com.douglas.salessystem.features.paymentmethod.controller;

import com.douglas.salessystem.features.paymentmethod.dto.PaymentMethodRequestDTO;
import com.douglas.salessystem.features.paymentmethod.dto.PaymentMethodResponseDTO;
import com.douglas.salessystem.features.paymentmethod.service.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodResponseDTO create(
            @RequestBody @Valid PaymentMethodRequestDTO dto) {

        return service.create(dto);
    }

    @GetMapping("/{id}")
    public PaymentMethodResponseDTO findById(
            @PathVariable UUID id) {

        return service.findById(id);
    }

    @GetMapping
    public List<PaymentMethodResponseDTO> findAll() {

        return service.findAll();
    }

    @PutMapping("/{id}")
    public PaymentMethodResponseDTO update(
            @PathVariable UUID id,
            @RequestBody @Valid PaymentMethodRequestDTO dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id) {

        service.delete(id);
    }
}
