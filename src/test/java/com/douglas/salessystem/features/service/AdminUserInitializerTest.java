package com.douglas.salessystem.features.service;

import com.douglas.salessystem.features.user.model.User;
import com.douglas.salessystem.features.user.repository.UserRepository;
import com.douglas.salessystem.features.user.service.AdminUserInitializer;
import com.douglas.salessystem.shared.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminUserInitializerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminUserInitializer adminUserInitializer;

    @Test
    void shouldCreateAdminUserWhenItDoesNotExist() throws Exception {
        when(userRepository.findByEmail("admin@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("12345")).thenReturn("encoded-12345");

        adminUserInitializer.run(new DefaultApplicationArguments(new String[0]));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertNotNull(savedUser);
        assertEquals("Admin", savedUser.getName());
        assertEquals("admin@email.com", savedUser.getEmail());
        assertEquals("encoded-12345", savedUser.getPassword());
        assertEquals(Role.ADMIN, savedUser.getRole());
        assertTrue(savedUser.getActive());
    }

    @Test
    void shouldUpdateExistingAdminUserOnStartup() throws Exception {
        User existingAdmin = User.builder()
                .id(UUID.randomUUID())
                .name("Old Admin")
                .email("admin@email.com")
                .password("old-password")
                .role(Role.SELLER)
                .active(false)
                .build();

        when(userRepository.findByEmail("admin@email.com")).thenReturn(Optional.of(existingAdmin));
        when(passwordEncoder.encode("12345")).thenReturn("encoded-12345");

        adminUserInitializer.run(new DefaultApplicationArguments(new String[0]));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(existingAdmin.getId(), savedUser.getId());
        assertEquals("Admin", savedUser.getName());
        assertEquals("admin@email.com", savedUser.getEmail());
        assertEquals("encoded-12345", savedUser.getPassword());
        assertEquals(Role.ADMIN, savedUser.getRole());
        assertTrue(savedUser.getActive());
    }
}
