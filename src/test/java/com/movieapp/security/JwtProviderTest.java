package com.movieapp.security;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setup() {
        jwtProvider = new JwtProvider();
        jwtProvider.jwtSecret = "test_secret_key";
        jwtProvider.jwtExpirationMs = 3600000;
    }

    @Test
    void generate_and_validate_token() {
        String token = jwtProvider.generateToken("user123");

        assertNotNull(token);
        assertTrue(jwtProvider.validateToken(token));
        assertEquals("user123", jwtProvider.extractUsername(token));
    }

    @Test
    void validate_invalid_token_returns_false() {
        String fake = "invalid.token.value";
        assertFalse(jwtProvider.validateToken(fake));
    }
}
