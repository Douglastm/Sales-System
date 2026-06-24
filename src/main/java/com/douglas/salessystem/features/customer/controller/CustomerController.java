package com.douglas.salessystem.features.customer.controller;

import com.douglas.salessystem.features.customer.dto.CustomerRequestDTO;
import com.douglas.salessystem.features.customer.dto.CustomerResponseDTO;
import com.douglas.salessystem.features.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDTO create(
            @RequestBody @Valid CustomerRequestDTO dto) {

        return service.create(dto);
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO findById(
            @PathVariable UUID id) {

        return service.findById(id);
    }

    @GetMapping
    public List<CustomerResponseDTO> findAll() {

        return service.findAll();
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO update(
            @PathVariable UUID id,
            @RequestBody @Valid CustomerRequestDTO dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id) {

        service.delete(id);
    }
}
