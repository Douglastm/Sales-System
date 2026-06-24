package com.douglas.salessystem.features.customer.mapper;

import com.douglas.salessystem.features.address.mapper.AddressMapper;
import com.douglas.salessystem.features.customer.dto.CustomerRequestDTO;
import com.douglas.salessystem.features.customer.dto.CustomerResponseDTO;
import com.douglas.salessystem.features.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final AddressMapper addressMapper;

    public Customer toEntity(CustomerRequestDTO dto) {

        return Customer.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .email(dto.email())
                .phone(dto.phone())
                .address(
                        dto.address() != null
                                ? addressMapper.toEntity(dto.address())
                                : null
                )
                .active(true)
                .build();
    }

    public CustomerResponseDTO toResponse(Customer customer) {

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress() != null
                        ? addressMapper.toResponse(customer.getAddress())
                        : null,
                customer.getActive()
        );
    }
}
