package com.douglas.salessystem.features.category.service;

import com.douglas.salessystem.features.category.dto.CategoryRequestDTO;
import com.douglas.salessystem.features.category.dto.CategoryResponseDTO;
import com.douglas.salessystem.features.category.mapper.CategoryMapper;
import com.douglas.salessystem.features.category.model.Category;
import com.douglas.salessystem.features.category.repository.CategoryRepository;
import com.douglas.salessystem.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryResponseDTO create(CategoryRequestDTO dto) {

        if (repository.existsByNameIgnoreCase(dto.name())) {
            throw new IllegalArgumentException(
                    "Category already exists."
            );
        }

        Category category = mapper.toEntity(dto);

        Category savedCategory = repository.save(category);

        return mapper.toResponse(savedCategory);
    }

    public CategoryResponseDTO findById(UUID id) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Category not found."
                        ));

        return mapper.toResponse(category);
    }

    public List<CategoryResponseDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CategoryResponseDTO update(
            UUID id,
            CategoryRequestDTO dto
    ) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Category not found."
                        ));

        category.setName(dto.name());
        category.setDescription(dto.description());

        Category updatedCategory =
                repository.save(category);

        return mapper.toResponse(updatedCategory);
    }

    public void delete(UUID id) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Category not found."
                        ));

        repository.delete(category);
    }
}
