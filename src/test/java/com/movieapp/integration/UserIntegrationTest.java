package com.movieapp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.api.rest.dto.request.CreateUserRequest;
import com.movieapp.security.JwtProvider;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private WebTestClient webClient;

    @Test
    @Order(0)
    void should_confirm_database_connection_via_actuator() {
        String token = jwtProvider.generateToken("admin"); // méthode que tu dois déjà avoir

        System.out.println(">>> [TEST] Vérification de la connexion à la base via /actuator/health/db");

        webClient.get()
                .uri("/actuator/health")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("UP");
    }


    @Test
    @Order(1)
    void should_create_user() {
        System.out.println(">>> [TEST] Création d'un utilisateur");

        String token = jwtProvider.generateToken("admin"); // ou "admin1" si existant et reconnu

        CreateUserRequest req = new CreateUserRequest();
        req.setUsername("john");
        req.setEmail("john@example.com");
        req.setPassword("password123");
        req.setRoleId(1); // ce rôle doit exister en BDD

        System.out.println(">>> Requête de création utilisateur : " + req);

        webClient.post()
                .uri("/api/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk();

        System.out.println(">>> [OK] Utilisateur créé avec succès.");
    }

    @Test
    @Order(99)
    void cleanup_user_john() {
        String token = jwtProvider.generateToken("admin");

        webClient.delete()
                .uri("/api/users/username/john")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isNoContent();
    }
}