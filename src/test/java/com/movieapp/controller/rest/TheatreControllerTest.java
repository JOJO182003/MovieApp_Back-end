package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.TheatreController;
import com.movieapp.api.rest.dto.request.CreateTheatreRequest;
import com.movieapp.application.service.TheatreService;
import com.movieapp.domain.model.Theatre;
import com.movieapp.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.movieapp.util.TestDataFactory.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TheatreController.class)
@Import(TheatreControllerTest.SecurityOverrideConfig.class)
class TheatreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TheatreService theatreService;

    @Test
    void should_return_theatres() throws Exception {
        Theatre theatre = theatreLyon1();

        when(theatreService.getAll()).thenReturn(List.of(theatre));

        mockMvc.perform(get("/api/theatres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gaumont 1"))
                .andExpect(jsonPath("$[0].city").value("Lyon"))
                .andExpect(jsonPath("$[0].address").value("94 rue de la Ville, Lyon"));
    }

    @Test
    void should_return_single_theatre() throws Exception {
        Theatre theatre = theatreLyon1();

        when(theatreService.getById(1)).thenReturn(theatre);

        mockMvc.perform(get("/api/theatres/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gaumont 1"))
                .andExpect(jsonPath("$.city").value("Lyon"))
                .andExpect(jsonPath("$.address").value("94 rue de la Ville, Lyon"));
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_create_theatre() throws Exception {
        CreateTheatreRequest request = TestDataFactory.createTheatreRequestLyon1();
        Theatre theatre = theatreLyon1();

        when(theatreService.create(Mockito.any())).thenReturn(theatre);

        mockMvc.perform(post("/api/theatres")
                        .contentType("application/json")
                        .content("""
                            {
                                "name": "Gaumont 1",
                                "city": "Lyon",
                                "address": "94 rue de la Ville, Lyon"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gaumont 1"))
                .andExpect(jsonPath("$.city").value("Lyon"))
                .andExpect(jsonPath("$.address").value("94 rue de la Ville, Lyon"));
    }

    @Test
    void should_deny_create_theatre() throws Exception {
        mockMvc.perform(post("/api/theatres")
                        .contentType("application/json")
                        .content("""
                {
                    "name": "Gaumont 1",
                    "city": "Lyon",
                    "address": "94 rue de la Ville, Lyon"
                }
            """))
                .andExpect(status().isUnauthorized()); // ou .isForbidden() si JWT fourni mais sans rôle
    }


    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_delete_theatre() throws Exception {
        mockMvc.perform(delete("/api/theatres/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(theatreService).delete(1);
    }

    @Test
    void should_deny_delete_theatre() throws Exception {
        mockMvc.perform(delete("/api/theatres/1"))
                .andExpect(status().isUnauthorized()); // ou isForbidden() selon le contexte
    }

    @TestConfiguration
    static class SecurityOverrideConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET, "/api/theatres/**").permitAll() // ✅ autorise GET
                            .requestMatchers("/api/theatres/**").hasRole("ADMIN")            // ❌ POST/DELETE doivent être ADMIN
                            .anyRequest().permitAll()
                    )
                    .httpBasic(withDefaults())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }
        @Bean
        public TheatreService theatreService() {
            return Mockito.mock(TheatreService.class);
        }
    }
}
