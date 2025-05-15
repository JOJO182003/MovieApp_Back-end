package com.movieapp.integration;

import com.movieapp.api.rest.dto.request.CreateMovieRequest;
import com.movieapp.security.JwtProvider;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @Order(1)
    void should_create_movie() {
        String token = jwtProvider.generateToken("admin");

        CreateMovieRequest req = new CreateMovieRequest();
        req.setTitle("Matrix");
        req.setDurationMinutes(120);

        webClient.post()
                .uri("/api/movies")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    void cleanup_movie_test() {
        String token = jwtProvider.generateToken("admin");

        webClient.delete()
                .uri("/api/movies/title/Matrix")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isNoContent();
    }
}
