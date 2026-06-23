package com.douglas.salessystem.features.user.service;

import com.douglas.salessystem.features.user.dto.UserRequestDTO;
import com.douglas.salessystem.features.user.dto.UserResponseDTO;
import com.douglas.salessystem.features.user.mapper.UserMapper;
import com.douglas.salessystem.features.user.model.User;
import com.douglas.salessystem.features.user.repository.UserRepository;
import com.douglas.salessystem.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO create(UserRequestDTO dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email already registered.");
        }

        User user = mapper.toEntity(dto);

        user.setPassword(
                passwordEncoder.encode(dto.password())
        );

        User savedUser = repository.save(user);

        return mapper.toResponse(savedUser);
    }

    public UserResponseDTO findById(UUID id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("User not found with id: " + id));

        return mapper.toResponse(user);
    }

    public List<UserResponseDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void delete(UUID id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("User not found with id: " + id));

        repository.delete(user);
    }

    public UserResponseDTO update(UUID id, UserRequestDTO dto) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("User not found with id: " + id));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setRole(dto.role());

        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(
                    passwordEncoder.encode(dto.password())
            );
        }

        User updatedUser = repository.save(user);

        return mapper.toResponse(updatedUser);
    }
}
