package com.douglas.salessystem.features.product.service;

import com.douglas.salessystem.features.category.model.Category;
import com.douglas.salessystem.features.category.repository.CategoryRepository;
import com.douglas.salessystem.features.product.dto.ProductRequestDTO;
import com.douglas.salessystem.features.product.dto.ProductResponseDTO;
import com.douglas.salessystem.features.product.mapper.ProductMapper;
import com.douglas.salessystem.features.product.model.Product;
import com.douglas.salessystem.features.product.repository.ProductRepository;
import com.douglas.salessystem.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductResponseDTO create(ProductRequestDTO dto) {

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() ->
                        new NotFoundException("Category not found"));

        Product product = Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .stockQuantity(dto.stockQuantity())
                .category(category)
                .active(true)
                .build();

        Product saved = repository.save(product);

        return mapper.toResponse(saved);
    }

    public ProductResponseDTO findById(UUID id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Product not found"));

        return mapper.toResponse(product);
    }

    public List<ProductResponseDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ProductResponseDTO update(UUID id, ProductRequestDTO dto) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Product not found"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() ->
                        new NotFoundException("Category not found"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setCategory(category);

        Product updated = repository.save(product);

        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Product not found"));

        repository.delete(product);
    }
}
