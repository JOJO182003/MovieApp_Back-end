package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.RoleController;
import com.movieapp.application.service.RoleService;
import com.movieapp.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
@Import(RoleControllerTest.SecurityOverrideConfig.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleService roleService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_return_roles() throws Exception {
        Role role = new Role(1, "ADMIN");
        when(roleService.getAllRoles()).thenReturn(List.of(role));

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ADMIN"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_return_role_by_id() throws Exception {
        Role role = new Role(2, "CINEMA_OWNER");
        when(roleService.getById(2)).thenReturn(role);

        mockMvc.perform(get("/api/roles/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CINEMA_OWNER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_create_role() throws Exception {
        Role role = new Role(3, "EMPLOYEE");
        when(roleService.create("EMPLOYEE")).thenReturn(role);

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .content("""
                            { "name": "EMPLOYEE" }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("EMPLOYEE"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_update_role() throws Exception {
        Role role = new Role(4, "MODERATOR");
        when(roleService.update(4, "MODERATOR")).thenReturn(role);

        mockMvc.perform(put("/api/roles/4")
                        .contentType("application/json")
                        .content("""
                            { "name": "MODERATOR" }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MODERATOR"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_delete_role() throws Exception {
        mockMvc.perform(delete("/api/roles/5"))
                .andExpect(status().isNoContent());

        // Optionnel : vérifie si delete() a bien été invoqué
        verify(roleService).delete(5);
    }

    @TestConfiguration
    static class SecurityOverrideConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/roles/**").hasRole("ADMIN")
                            .anyRequest().permitAll()
                    )
                    .httpBasic(withDefaults())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }

        @Bean
        public RoleService roleService() {
            return Mockito.mock(RoleService.class);
        }
    }
}
