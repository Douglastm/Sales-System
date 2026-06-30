package com.douglas.salessystem.features.service;

import com.douglas.salessystem.features.address.dto.AddressRequestDTO;
import com.douglas.salessystem.features.address.mapper.AddressMapper;
import com.douglas.salessystem.features.address.model.Address;
import com.douglas.salessystem.features.category.dto.CategoryRequestDTO;
import com.douglas.salessystem.features.category.dto.CategoryResponseDTO;
import com.douglas.salessystem.features.category.mapper.CategoryMapper;
import com.douglas.salessystem.features.category.model.Category;
import com.douglas.salessystem.features.category.repository.CategoryRepository;
import com.douglas.salessystem.features.category.service.CategoryService;
import com.douglas.salessystem.features.customer.dto.CustomerRequestDTO;
import com.douglas.salessystem.features.customer.dto.CustomerResponseDTO;
import com.douglas.salessystem.features.customer.mapper.CustomerMapper;
import com.douglas.salessystem.features.customer.model.Customer;
import com.douglas.salessystem.features.customer.repository.CustomerRepository;
import com.douglas.salessystem.features.customer.service.CustomerService;
import com.douglas.salessystem.features.product.dto.ProductRequestDTO;
import com.douglas.salessystem.features.product.dto.ProductResponseDTO;
import com.douglas.salessystem.features.product.mapper.ProductMapper;
import com.douglas.salessystem.features.product.model.Product;
import com.douglas.salessystem.features.product.repository.ProductRepository;
import com.douglas.salessystem.features.product.service.ProductService;
import com.douglas.salessystem.features.user.dto.UserRequestDTO;
import com.douglas.salessystem.features.user.dto.UserResponseDTO;
import com.douglas.salessystem.features.user.mapper.UserMapper;
import com.douglas.salessystem.features.user.model.User;
import com.douglas.salessystem.features.user.repository.UserRepository;
import com.douglas.salessystem.features.user.service.UserService;
import com.douglas.salessystem.shared.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServicesTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void shouldRegisterUserCorrectly() {
        UserRequestDTO request = new UserRequestDTO(
                "Douglas",
                "douglas@email.com",
                "123456",
                Role.ADMIN
        );
        User mappedUser = User.builder()
                .name(request.name())
                .email(request.email())
                .role(request.role())
                .active(true)
                .build();
        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .email(request.email())
                .password("encoded-123456")
                .role(request.role())
                .active(true)
                .build();
        UserResponseDTO expected = new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getActive()
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(mappedUser);
        when(passwordEncoder.encode(request.password())).thenReturn("encoded-123456");
        when(userRepository.save(mappedUser)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(expected);

        UserResponseDTO response = userService.create(request);

        assertEquals(expected, response);
        assertEquals("encoded-123456", mappedUser.getPassword());
        assertTrue(mappedUser.getActive());
    }

    @Test
    void shouldRegisterCustomerCorrectly() {
        AddressRequestDTO addressRequest = new AddressRequestDTO(
                "01001-000",
                "Rua A",
                "123",
                "Apto 10",
                "Centro",
                "Sao Paulo",
                "SP"
        );
        CustomerRequestDTO request = new CustomerRequestDTO(
                "Maria",
                "12345678909",
                "maria@email.com",
                "11999999999",
                addressRequest
        );
        Address address = Address.builder()
                .zipCode(addressRequest.zipCode())
                .street(addressRequest.street())
                .number(addressRequest.number())
                .complement(addressRequest.complement())
                .district(addressRequest.district())
                .city(addressRequest.city())
                .state(addressRequest.state())
                .build();
        Customer mappedCustomer = Customer.builder()
                .name(request.name())
                .cpf(request.cpf())
                .email(request.email())
                .phone(request.phone())
                .address(address)
                .active(true)
                .build();
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .cpf(request.cpf())
                .email(request.email())
                .phone(request.phone())
                .address(address)
                .active(true)
                .build();
        CustomerResponseDTO expected = new CustomerResponseDTO(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getCpf(),
                savedCustomer.getEmail(),
                savedCustomer.getPhone(),
                null,
                savedCustomer.getActive()
        );

        when(customerRepository.existsByCpf(request.cpf())).thenReturn(false);
        when(customerRepository.existsByEmail(request.email())).thenReturn(false);
        when(customerMapper.toEntity(request)).thenReturn(mappedCustomer);
        when(customerRepository.save(mappedCustomer)).thenReturn(savedCustomer);
        when(customerMapper.toResponse(savedCustomer)).thenReturn(expected);

        CustomerResponseDTO response = customerService.create(request);

        assertEquals(expected, response);
        assertNotNull(mappedCustomer.getAddress());
        assertEquals("01001-000", mappedCustomer.getAddress().getZipCode());
        assertTrue(mappedCustomer.getActive());
    }

    @Test
    void shouldRegisterProductCorrectly() {
        UUID categoryId = UUID.randomUUID();
        ProductRequestDTO request = new ProductRequestDTO(
                "Notebook",
                "16GB RAM",
                new BigDecimal("3500.00"),
                5,
                categoryId
        );
        Category category = Category.builder()
                .id(categoryId)
                .name("Informatica")
                .description("Produtos de TI")
                .active(true)
                .build();
        Product savedProduct = Product.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .category(category)
                .active(true)
                .build();
        ProductResponseDTO expected = new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStockQuantity(),
                category.getId(),
                category.getName(),
                savedProduct.getActive()
        );

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(productMapper.toResponse(savedProduct)).thenReturn(expected);

        ProductResponseDTO response = productService.create(request);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(expected, response);
        assertEquals("Notebook", capturedProduct.getName());
        assertEquals(category, capturedProduct.getCategory());
        assertTrue(capturedProduct.getActive());
    }

    @Test
    void shouldRegisterCategoryCorrectly() {
        CategoryRequestDTO request = new CategoryRequestDTO(
                "Eletronicos",
                "Categoria de eletronicos"
        );
        Category mappedCategory = Category.builder()
                .name(request.name())
                .description(request.description())
                .active(true)
                .build();
        Category savedCategory = Category.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .active(true)
                .build();
        CategoryResponseDTO expected = new CategoryResponseDTO(
                savedCategory.getId(),
                savedCategory.getName(),
                savedCategory.getDescription(),
                savedCategory.getActive()
        );

        when(categoryRepository.existsByNameIgnoreCase(request.name())).thenReturn(false);
        when(categoryMapper.toEntity(request)).thenReturn(mappedCategory);
        when(categoryRepository.save(mappedCategory)).thenReturn(savedCategory);
        when(categoryMapper.toResponse(savedCategory)).thenReturn(expected);

        CategoryResponseDTO response = categoryService.create(request);

        assertEquals(expected, response);
        assertTrue(mappedCategory.getActive());
    }
}
