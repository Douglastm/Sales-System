package com.douglas.salessystem.features.customer.service;

import com.douglas.salessystem.features.address.mapper.AddressMapper;
import com.douglas.salessystem.features.customer.dto.CustomerRequestDTO;
import com.douglas.salessystem.features.customer.dto.CustomerResponseDTO;
import com.douglas.salessystem.features.customer.mapper.CustomerMapper;
import com.douglas.salessystem.features.customer.model.Customer;
import com.douglas.salessystem.features.customer.repository.CustomerRepository;
import com.douglas.salessystem.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final AddressMapper addressMapper;

    public CustomerResponseDTO create(CustomerRequestDTO dto) {

        if (repository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException(
                    "CPF already registered."
            );
        }

        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException(
                    "Email already registered."
            );
        }

        Customer customer = mapper.toEntity(dto);

        Customer savedCustomer = repository.save(customer);

        return mapper.toResponse(savedCustomer);
    }

    public CustomerResponseDTO findById(UUID id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Customer not found."
                        ));

        return mapper.toResponse(customer);
    }

    public List<CustomerResponseDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CustomerResponseDTO update(
            UUID id,
            CustomerRequestDTO dto
    ) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Customer not found."
                        ));

        if (!customer.getCpf().equals(dto.cpf())
                && repository.existsByCpf(dto.cpf())) {

            throw new IllegalArgumentException(
                    "CPF already registered."
            );
        }

        if (!customer.getEmail().equals(dto.email())
                && repository.existsByEmail(dto.email())) {

            throw new IllegalArgumentException(
                    "Email already registered."
            );
        }

        customer.setName(dto.name());
        customer.setCpf(dto.cpf());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());

        if (dto.address() != null) {

            customer.setAddress(
                    addressMapper.toEntity(dto.address())
            );
        }

        Customer updatedCustomer =
                repository.save(customer);

        return mapper.toResponse(updatedCustomer);
    }

    public void delete(UUID id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Customer not found."
                        ));

        repository.delete(customer);
    }
}
