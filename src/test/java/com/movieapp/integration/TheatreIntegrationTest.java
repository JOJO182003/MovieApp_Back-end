package com.movieapp.integration;

import com.movieapp.api.rest.dto.request.CreateTheatreRequest;
import com.movieapp.security.JwtProvider;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TheatreIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @Order(1)
    void should_create_theatre() {
        System.out.println(">>> [TEST] Création d’un théâtre");

        String token = jwtProvider.generateToken("admin");

        CreateTheatreRequest request = new CreateTheatreRequest();
        request.setName("Théâtre Lumière");
        request.setCity("Lyon");
        request.setAddress("10 rue des étoiles");

        webClient.post()
                .uri("/api/theatres")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    void cleanup_theatre() {
        String token = jwtProvider.generateToken("admin");

        webClient.delete()
                .uri("/api/theatres/name/Théâtre Lumière")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isNoContent();
    }
}
