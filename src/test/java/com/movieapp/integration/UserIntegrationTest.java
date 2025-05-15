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
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://ep-young-boat-a4027g0r-pooler.us-east-1.aws.neon.tech:5432/moviePlanner",
        "spring.datasource.username=moviePlanner_owner",
        "spring.datasource.password=npg_Y9HzwWybKf6n",
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.jpa.show-sql=true",
        "spring.main.allow-bean-definition-overriding=true",
        "jwt.secret=Yk3YRvZnL8WXt2Fp7aU5XyqCEBmPGZY1wrEvmQmvVLFVhPbhC7yzPQzZwY5NuTz9", // ⚠️ clé base64 ou longue
        "jwt.expirationMs=3600000"
})
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
                .uri("/actuator/health/db")
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

        CreateUserRequest req = new CreateUserRequest();
        req.setUsername("john");
        req.setEmail("john@example.com");
        req.setPassword("password123");
        req.setRoleId(1); // ce rôle doit exister

        System.out.println(">>> Requête de création utilisateur : " + req);

        webClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk();

        System.out.println(">>> [OK] Utilisateur créé avec succès.");
    }


    @AfterEach
    void cleanup() {
        // Only try to delete if we're past the create test
        if (TestInfo.currentOrder > 0) {
            webClient.delete()
                    .uri("/api/users/username/john")
                    .exchange()
                    .expectStatus().isNoContent();
        }
    }

    // Helper class to track test execution order
    private static class TestInfo {
        private static int currentOrder = 0;
    }
}