package com.douglas.salessystem.features.customer.model;

import com.douglas.salessystem.features.address.model.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String phone;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable = false)
    private Boolean active;
}
