package com.douglas.salessystem.features.address.mapper;

import com.douglas.salessystem.features.address.dto.AddressRequestDTO;
import com.douglas.salessystem.features.address.dto.AddressResponseDTO;
import com.douglas.salessystem.features.address.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequestDTO dto) {

        return Address.builder()
                .zipCode(dto.zipCode())
                .street(dto.street())
                .number(dto.number())
                .complement(dto.complement())
                .district(dto.district())
                .city(dto.city())
                .state(dto.state())
                .build();
    }

    public AddressResponseDTO toResponse(Address address) {

        return new AddressResponseDTO(
                address.getId(),
                address.getZipCode(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getDistrict(),
                address.getCity(),
                address.getState()
        );
    }
}
