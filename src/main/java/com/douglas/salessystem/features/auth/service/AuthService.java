package com.douglas.salessystem.features.auth.service;

import com.douglas.salessystem.features.auth.dto.AuthRequestDTO;
import com.douglas.salessystem.features.auth.dto.AuthResponseDTO;
import com.douglas.salessystem.features.user.model.User;
import com.douglas.salessystem.features.user.repository.UserRepository;
import com.douglas.salessystem.shared.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO login(AuthRequestDTO dto) {

        User user = repository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid credentials"
                        ));

        boolean matches = passwordEncoder.matches(
                dto.password(),
                user.getPassword()
        );

        if (!matches) {
            throw new IllegalArgumentException(
                    "Invalid credentials"
            );
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token);
    }
}
