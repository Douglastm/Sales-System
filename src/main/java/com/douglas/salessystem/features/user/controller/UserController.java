package com.douglas.salessystem.features.user.controller;

import com.douglas.salessystem.features.user.dto.UserRequestDTO;
import com.douglas.salessystem.features.user.dto.UserResponseDTO;
import com.douglas.salessystem.features.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(
            @RequestBody @Valid UserRequestDTO dto) {

        return service.create(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(
            @PathVariable UUID id) {

        return service.findById(id);
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {

        return service.findAll();
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(
            @PathVariable UUID id,
            @RequestBody @Valid UserRequestDTO dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id) {

        service.delete(id);
    }
}