package com.douglas.salessystem.features.category.controller;

import com.douglas.salessystem.features.category.dto.CategoryRequestDTO;
import com.douglas.salessystem.features.category.dto.CategoryResponseDTO;
import com.douglas.salessystem.features.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO create(
            @RequestBody @Valid CategoryRequestDTO dto
    ) {

        return service.create(dto);
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO findById(
            @PathVariable UUID id
    ) {

        return service.findById(id);
    }

    @GetMapping
    public List<CategoryResponseDTO> findAll() {

        return service.findAll();
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO update(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryRequestDTO dto
    ) {

        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {

        service.delete(id);
    }
}
