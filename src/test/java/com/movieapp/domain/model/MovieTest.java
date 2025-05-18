package com.movieapp.domain.model;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void create_valid_movie_should_succeed() {
        Movie m = new Movie(1, "Inception", "Great film", 120, "EN", "Nolan", 12,
                LocalDate.of(2010, 7, 16), LocalDateTime.now(), LocalDateTime.now(), "test");

        assertEquals("Inception", m.getTitle());
        assertEquals("Nolan", m.getDirector());
    }

    @Test
    void create_movie_with_invalid_title_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie(0, " ", "x", 120, "FR", "Somebody", null,
                        LocalDate.now(), null, null, "test"));
    }

    @Test
    void create_movie_with_negative_duration_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new Movie(0, "Title", "x", -1, "FR", "X", null,
                        LocalDate.now(), null, null, "test"));
    }
}
