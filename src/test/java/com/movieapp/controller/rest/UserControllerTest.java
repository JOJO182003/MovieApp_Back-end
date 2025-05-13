package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.UserController;
import com.movieapp.api.rest.dto.request.CreateUserRequest;
import com.movieapp.api.rest.mapper.UserRestMapper;
import com.movieapp.application.service.RoleService;
import com.movieapp.application.service.UserService;
import com.movieapp.domain.model.Role;
import com.movieapp.domain.model.User;
import com.movieapp.util.TestDataFactory;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.SecurityOverrideConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    @WithMockUser(username = "simpleUser")
    void should_deny_access_to_user_without_admin_role() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_allow_admin_to_access_users() throws Exception {
        when(userService.getAll()).thenReturn(List.of(TestDataFactory.adminUser()));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("admin1"));
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_return_users() throws Exception {
        User u = TestDataFactory.adminUser();
        when(userService.getAll()).thenReturn(List.of(u));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("admin1"))
                .andExpect(jsonPath("$[0].email").value("admin1@movieapp.com"));
    }

    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_create_user() throws Exception {
        CreateUserRequest req = TestDataFactory.createUserRequest();

        User userToSave = new User(
                99, // un id fictif
                "newuser",
                "newuser@movieapp.com",
                "hashedPass",
                new Role(1, "ADMIN"),
                List.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );


        when(roleService.getById(1)).thenReturn(new Role(1, "ADMIN"));
        when(passwordEncoder.encode(req.getPassword())).thenReturn("hashedPass");
        when(userService.register(Mockito.any(User.class))).thenReturn(userToSave);

        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("""
                    {
                        "username": "newuser",
                        "email": "newuser@movieapp.com",
                        "password": "securePass123",
                        "roleId": 1
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("newuser@movieapp.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }


    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_return_user_by_id() throws Exception {
        User u = TestDataFactory.adminUser();
        when(userService.getById(1)).thenReturn(u);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin1"));
    }


    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void should_delete_user_by_id() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).delete(1);
    }

    @TestConfiguration
    static class SecurityOverrideConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/users/**").hasRole("ADMIN")  // <-- Ici le bon chemin
                            .anyRequest().permitAll()
                    )
                    .httpBasic(withDefaults())
                    .csrf(AbstractHttpConfigurer::disable); // désactive CSRF pour les tests
            return http.build();
        }

        @Bean public UserService userService() {
            return Mockito.mock(UserService.class);
        }
        @Bean public RoleService roleService() {
            return Mockito.mock(RoleService.class);
        }
        @Bean public PasswordEncoder passwordEncoder() {
            return Mockito.mock(PasswordEncoder.class);
        }

        @Bean
        public UserRestMapper userRestMapper() {
            return new UserRestMapper(); // ou un mock si le mapper est complexe
        }
    }
}
