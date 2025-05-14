package com.movieapp.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.movieapp.api.rest.controller.AuthController;
import com.movieapp.api.rest.dto.request.LoginRequest;
import com.movieapp.domain.model.CustomUserDetails;
import com.movieapp.security.AuthEntryPoint;
import com.movieapp.security.JwtProvider;
import com.movieapp.security.SecurityContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@Import({AuthControllerTest.TestSecurityConfig.class, AuthControllerTest.JacksonConfig.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private final String token = "fake.jwt.token";

    @BeforeEach
    void setup() {
        reset(authenticationManager, jwtProvider);
    }

    @Test
    void should_login_and_return_jwt() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("secret");

        CustomUserDetails userDetails = new CustomUserDetails(
                1,
                "testuser",
                "secret",
                List.of(new SimpleGrantedAuthority("CINEMA_OWNER"))
        );

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtProvider.generateToken("testuser")).thenReturn(token);

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void should_return_current_user_info() throws Exception {
        CustomUserDetails userDetails = new CustomUserDetails(
                1,
                "testuser",
                "secret",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        try (var mocked = Mockito.mockStatic(SecurityContextUtil.class)) {
            mocked.when(SecurityContextUtil::getCurrentUser).thenReturn(userDetails);

            mockMvc.perform(get("/api/auth/me"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.username").value("testuser"))
                    .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
        }
    }

    @Test
    void should_call_auth_entrypoint_on_unauthorized() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Non authentifié"))
                .andExpect(jsonPath("$.path").value("/api/auth/me"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_401_if_not_authenticated() throws Exception {
        try (var mocked = Mockito.mockStatic(SecurityContextUtil.class)) {
            mocked.when(SecurityContextUtil::getCurrentUser).thenReturn(null);

            mockMvc.perform(get("/api/auth/me"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Non authentifié"));
        }
    }

    @Configuration
    static class TestSecurityConfig {
        @Bean
        @Primary
        public AuthenticationManager authenticationManager() {
            return mock(AuthenticationManager.class);
        }

        @Bean
        public JwtProvider jwtProvider() {
            return mock(JwtProvider.class);
        }

        @Bean
        public AuthEntryPoint authEntryPoint(ObjectMapper objectMapper) {
            return new AuthEntryPoint(objectMapper);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthEntryPoint authEntryPoint) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPoint))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/auth/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    .build();
        }
    }

    @Configuration
    static class JacksonConfig {
        @Bean
        @Primary
        public ObjectMapper objectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper;
        }
    }
}
