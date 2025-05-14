package com.movieapp.security;

import com.movieapp.application.service.CustomUserDetailsService;
import com.movieapp.domain.model.Role;
import com.movieapp.domain.model.User;
import com.movieapp.domain.repository.UserRepository;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private CustomUserDetailsService service;
    private UserRepository userRepo;

    @BeforeEach
    void init() {
        userRepo = mock(UserRepository.class);
        service = new CustomUserDetailsService(userRepo);
    }

    @Test
    void loadUserByUsername_returns_userdetails() {
        var user = new User(1, "john", "john@example.com", "pass", new Role(1, "ADMIN"), null, null, null);
        when(userRepo.findByUsername("john")).thenReturn(Optional.of(user));

        var details = service.loadUserByUsername("john");

        assertEquals("john", details.getUsername());
        assertEquals("pass", details.getPassword());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_not_found_throws_exception() {
        when(userRepo.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> service.loadUserByUsername("ghost"));
    }
}
