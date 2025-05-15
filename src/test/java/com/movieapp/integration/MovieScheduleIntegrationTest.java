package com.movieapp.integration;

import com.movieapp.api.rest.dto.request.CreateMovieScheduleRequest;
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

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieScheduleIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @Order(1)
    void should_create_movie_schedule() {
        String token = jwtProvider.generateToken("admin");

        CreateMovieScheduleRequest req = new CreateMovieScheduleRequest();
        req.setMovieId(1);
        req.setTheatreId(1);
        req.setStartTime(LocalDateTime.now().plusDays(1));

        webClient.post()
                .uri("/api/schedules")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    void cleanup_movie_schedule_test() {
        String token = jwtProvider.generateToken("admin");

        // Adapte selon ton API si elle permet de delete par ID ou autre clé
        webClient.delete()
                .uri("/api/schedules/id/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isNoContent();
    }
}
