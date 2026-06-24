package com.douglas.salessystem.features.category.mapper;

import com.douglas.salessystem.features.category.dto.CategoryRequestDTO;
import com.douglas.salessystem.features.category.dto.CategoryResponseDTO;
import com.douglas.salessystem.features.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO dto) {

        return Category.builder()
                .name(dto.name())
                .description(dto.description())
                .active(true)
                .build();
    }

    public CategoryResponseDTO toResponse(Category category) {

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getActive()
        );
    }
}
