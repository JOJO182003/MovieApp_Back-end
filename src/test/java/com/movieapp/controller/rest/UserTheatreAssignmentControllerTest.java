package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.UserTheatreAssignmentController;
import com.movieapp.application.service.UserTheatreAssignmentService;
import com.movieapp.util.TestDataFactory;

import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserTheatreAssignmentController.class)
@Import(UserTheatreAssignmentControllerTest.SecurityOverrideConfig.class)
class UserTheatreAssignmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTheatreAssignmentService service;

    @Test
    void should_deny_access_to_unauthenticated_user() throws Exception {
        mockMvc.perform(get("/api/assignments"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "simpleUser")
    void should_deny_access_to_user_without_proper_role() throws Exception {
        mockMvc.perform(get("/api/assignments"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_allow_access_to_admin() throws Exception {
        when(service.getAll()).thenReturn(List.of(TestDataFactory.assignmentBdxOwner()));

        mockMvc.perform(get("/api/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("owner_bdx"));
    }


    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_return_all_assignments() throws Exception {
        when(service.getAll()).thenReturn(List.of(TestDataFactory.assignmentBdxOwner()));

        mockMvc.perform(get("/api/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("owner_bdx"))
                .andExpect(jsonPath("$[0].theatreName").value("CGR Bordeaux"));
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_assign_user_to_theatre() throws Exception {
        mockMvc.perform(post("/api/assignments")
                        .contentType("application/json")
                        .content("""
                                {
                                    "userId": 33,
                                    "theatreId": 11
                                }
                                """))
                .andExpect(status().isOk());

        verify(service, times(1)).assignUserToTheatre(33, 11);
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_remove_assignment() throws Exception {
        mockMvc.perform(delete("/api/assignments")
                        .param("userId", "33")
                        .param("theatreId", "11"))
                .andExpect(status().isNoContent());

        verify(service).removeAssignment(33, 11);
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_return_assignments_by_user() throws Exception {
        when(service.getByUser(33)).thenReturn(List.of(TestDataFactory.assignmentBdxOwner()));

        mockMvc.perform(get("/api/assignments/user/33"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(33));
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_return_assignments_by_theatre() throws Exception {
        when(service.getByTheatre(11)).thenReturn(List.of(TestDataFactory.assignmentBdxOwner()));

        mockMvc.perform(get("/api/assignments/theatre/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].theatreId").value(11));
    }


    // Supprime TestSecurityConfig, remplace-la par :
    @TestConfiguration
    static class SecurityOverrideConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/assignments/**").hasRole("ADMIN")
                            .anyRequest().permitAll()
                    )
                    .httpBasic(withDefaults())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }

        @Bean
        public UserTheatreAssignmentService service() {
            return mock(UserTheatreAssignmentService.class);
        }
    }
}
