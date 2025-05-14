package com.movieapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.*;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock private CustomUserDetailsService userDetailsService;
    @Mock private JwtProvider jwtProvider;
    @Mock private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService);
    }

    @Test
    void doFilter_with_valid_token_sets_authentication() throws Exception {
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid.token");

        when(jwtProvider.validateToken("valid.token")).thenReturn(true);
        when(jwtProvider.extractUsername("valid.token")).thenReturn("john");
        when(userDetailsService.loadUserByUsername("john")).thenReturn(
                org.springframework.security.core.userdetails.User
                        .withUsername("john").password("x").roles("USER").build()
        );

        var response = new MockHttpServletResponse();
        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
