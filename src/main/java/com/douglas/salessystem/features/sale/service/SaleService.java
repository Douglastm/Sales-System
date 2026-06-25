package com.douglas.salessystem.features.sale.service;

import com.douglas.salessystem.features.customer.model.Customer;
import com.douglas.salessystem.features.customer.repository.CustomerRepository;
import com.douglas.salessystem.features.paymentmethod.model.PaymentMethod;
import com.douglas.salessystem.features.paymentmethod.repository.PaymentMethodRepository;
import com.douglas.salessystem.features.product.model.Product;
import com.douglas.salessystem.features.product.repository.ProductRepository;
import com.douglas.salessystem.features.sale.dto.SaleRequestDTO;
import com.douglas.salessystem.features.sale.dto.SaleResponseDTO;
import com.douglas.salessystem.features.sale.mapper.SaleMapper;
import com.douglas.salessystem.features.sale.model.Sale;
import com.douglas.salessystem.shared.enums.SaleStatus;
import com.douglas.salessystem.features.sale.repository.SaleRepository;
import com.douglas.salessystem.features.saleitem.dto.SaleItemRequestDTO;
import com.douglas.salessystem.features.saleitem.model.SaleItem;
import com.douglas.salessystem.features.user.model.User;
import com.douglas.salessystem.features.user.repository.UserRepository;
import com.douglas.salessystem.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    private final SaleMapper mapper;

    @Transactional
    public SaleResponseDTO create(SaleRequestDTO dto) {

        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() ->
                        new NotFoundException("Customer not found."));

        User seller = userRepository.findById(dto.sellerId())
                .orElseThrow(() ->
                        new NotFoundException("Seller not found."));

        PaymentMethod paymentMethod =
                paymentMethodRepository.findById(dto.paymentMethodId())
                        .orElseThrow(() ->
                                new NotFoundException("Payment method not found."));

        Sale sale = Sale.builder()
                .customer(customer)
                .seller(seller)
                .paymentMethod(paymentMethod)
                .saleDate(LocalDateTime.now())
                .status(SaleStatus.COMPLETED)
                .build();

        List<SaleItem> items = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleItemRequestDTO itemDTO : dto.items()) {

            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() ->
                            new NotFoundException("Product not found."));

            if (product.getStockQuantity() < itemDTO.quantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }

            BigDecimal subtotal =
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(itemDTO.quantity()));

            SaleItem saleItem = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemDTO.quantity())
                    .unitPrice(product.getPrice())
                    .subtotal(subtotal)
                    .build();

            items.add(saleItem);

            totalAmount = totalAmount.add(subtotal);

            product.setStockQuantity(
                    product.getStockQuantity() - itemDTO.quantity()
            );

            productRepository.save(product);
        }

        sale.setItems(items);

        sale.setTotalAmount(totalAmount);

        Sale savedSale = saleRepository.save(sale);

        return mapper.toResponse(savedSale);
    }

    public SaleResponseDTO findById(UUID id) {

        Sale sale = saleRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Sale not found."));

        return mapper.toResponse(sale);
    }

    public List<SaleResponseDTO> findAll() {

        return saleRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public SaleResponseDTO cancel(UUID id) {

        Sale sale = saleRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Sale not found."));

        if (sale.getStatus() == SaleStatus.CANCELED) {
            throw new IllegalArgumentException(
                    "Sale has already been canceled."
            );
        }

        for (SaleItem item : sale.getItems()) {

            Product product = item.getProduct();

            product.setStockQuantity(
                    product.getStockQuantity() + item.getQuantity()
            );

            productRepository.save(product);
        }

        sale.setStatus(SaleStatus.CANCELED);

        Sale canceledSale = saleRepository.save(sale);

        return mapper.toResponse(canceledSale);
    }

}
