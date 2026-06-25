package com.douglas.salessystem.features.paymentmethod.service;

import com.douglas.salessystem.features.paymentmethod.dto.PaymentMethodRequestDTO;
import com.douglas.salessystem.features.paymentmethod.dto.PaymentMethodResponseDTO;
import com.douglas.salessystem.features.paymentmethod.mapper.PaymentMethodMapper;
import com.douglas.salessystem.features.paymentmethod.model.PaymentMethod;
import com.douglas.salessystem.features.paymentmethod.repository.PaymentMethodRepository;
import com.douglas.salessystem.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository repository;
    private final PaymentMethodMapper mapper;

    public PaymentMethodResponseDTO create(
            PaymentMethodRequestDTO dto) {

        if (repository.existsByNameIgnoreCase(dto.name())) {
            throw new IllegalArgumentException(
                    "Payment method already exists."
            );
        }

        PaymentMethod paymentMethod =
                mapper.toEntity(dto);

        return mapper.toResponse(
                repository.save(paymentMethod)
        );
    }

    public PaymentMethodResponseDTO findById(UUID id) {

        PaymentMethod paymentMethod =
                repository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Payment method not found."
                                ));

        return mapper.toResponse(paymentMethod);
    }

    public List<PaymentMethodResponseDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public PaymentMethodResponseDTO update(
            UUID id,
            PaymentMethodRequestDTO dto) {

        PaymentMethod paymentMethod =
                repository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Payment method not found."
                                ));

        paymentMethod.setName(dto.name());
        paymentMethod.setDescription(dto.description());

        return mapper.toResponse(
                repository.save(paymentMethod)
        );
    }

    public void delete(UUID id) {

        PaymentMethod paymentMethod =
                repository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Payment method not found."
                                ));

        repository.delete(paymentMethod);
    }
}
