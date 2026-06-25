package com.douglas.salessystem.features.paymentmethod.mapper;

import com.douglas.salessystem.features.paymentmethod.dto.PaymentMethodRequestDTO;
import com.douglas.salessystem.features.paymentmethod.dto.PaymentMethodResponseDTO;
import com.douglas.salessystem.features.paymentmethod.model.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {

    public PaymentMethod toEntity(PaymentMethodRequestDTO dto) {

        return PaymentMethod.builder()
                .name(dto.name())
                .description(dto.description())
                .active(true)
                .build();
    }

    public PaymentMethodResponseDTO toResponse(PaymentMethod paymentMethod) {

        return new PaymentMethodResponseDTO(
                paymentMethod.getId(),
                paymentMethod.getName(),
                paymentMethod.getDescription(),
                paymentMethod.getActive()
        );
    }
}
