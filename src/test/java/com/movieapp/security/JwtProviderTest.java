package com.movieapp.security;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setup() {
        jwtProvider = new JwtProvider();
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        jwtProvider.jwtSecret = Encoders.BASE64.encode(key.getEncoded());

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

