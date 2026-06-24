package com.douglas.salessystem.features.product.controller;

import com.douglas.salessystem.features.product.dto.ProductRequestDTO;
import com.douglas.salessystem.features.product.dto.ProductResponseDTO;
import com.douglas.salessystem.features.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(
            @RequestBody @Valid ProductRequestDTO dto) {

        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(
            @PathVariable UUID id) {

        return service.findById(id);
    }

    @GetMapping
    public List<ProductResponseDTO> findAll() {

        return service.findAll();
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequestDTO dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id) {

        service.delete(id);
    }
}
