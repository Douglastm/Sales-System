package com.douglas.salessystem.features.auth.controller;

import com.douglas.salessystem.features.auth.dto.AuthRequestDTO;
import com.douglas.salessystem.features.auth.dto.AuthResponseDTO;
import com.douglas.salessystem.features.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public AuthResponseDTO login(
            @RequestBody @Valid AuthRequestDTO dto) {

        return service.login(dto);
    }

}
