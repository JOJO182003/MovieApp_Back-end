package com.movieapp.exception;

import com.movieapp.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_returns_404() {
        NotFoundException ex = new NotFoundException("Not found");
        ResponseEntity<Object> response = handler.handleNotFound(ex, null);

        assertEquals(404, response.getStatusCodeValue());
        Map body = (Map) response.getBody();
        assertEquals("Not found", body.get("message"));
    }

    @Test
    void handleGeneric_returns_500() {
        Exception ex = new RuntimeException("Oops");
        ResponseEntity<Object> response = handler.handleGeneric(ex, null);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(((Map<?, ?>) response.getBody()).get("message").toString().contains("Oops"));
    }
}
