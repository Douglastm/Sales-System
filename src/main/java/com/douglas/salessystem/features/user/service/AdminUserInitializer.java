package com.douglas.salessystem.features.user.service;

import com.douglas.salessystem.features.user.model.User;
import com.douglas.salessystem.features.user.repository.UserRepository;
import com.douglas.salessystem.shared.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {

    private static final String ADMIN_NAME = "Admin";
    private static final String ADMIN_EMAIL = "admin@email.com";
    private static final String ADMIN_PASSWORD = "12345";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        User adminUser = userRepository.findByEmail(ADMIN_EMAIL)
                .orElseGet(() -> User.builder().email(ADMIN_EMAIL).build());

        adminUser.setName(ADMIN_NAME);
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        adminUser.setRole(Role.ADMIN);
        adminUser.setActive(true);

        userRepository.save(adminUser);
    }
}
