package com.movieapp.integration;

import com.movieapp.api.rest.dto.request.CreateRoleRequest;
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
public class RoleIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @Order(1)
    void should_create_role() {
        String token = jwtProvider.generateToken("admin");

        CreateRoleRequest req = new CreateRoleRequest();
        req.setName("TEST_ROLE");

        webClient.post()
                .uri("/api/roles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    void cleanup_role_test() {
        String token = jwtProvider.generateToken("admin");

        webClient.delete()
                .uri("/api/roles/name/TEST_ROLE")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isNoContent();
    }
}
