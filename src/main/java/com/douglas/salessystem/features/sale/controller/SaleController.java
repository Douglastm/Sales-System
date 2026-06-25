package com.douglas.salessystem.features.sale.controller;

import com.douglas.salessystem.features.sale.dto.SaleRequestDTO;
import com.douglas.salessystem.features.sale.dto.SaleResponseDTO;
import com.douglas.salessystem.features.sale.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponseDTO create(
            @RequestBody @Valid SaleRequestDTO dto) {

        return service.create(dto);
    }

    @GetMapping("/{id}")
    public SaleResponseDTO findById(
            @PathVariable UUID id) {

        return service.findById(id);
    }

    @GetMapping
    public List<SaleResponseDTO> findAll() {

        return service.findAll();
    }

    @PatchMapping("/{id}/cancel")
    public SaleResponseDTO cancel(
            @PathVariable UUID id) {

        return service.cancel(id);
    }

}
