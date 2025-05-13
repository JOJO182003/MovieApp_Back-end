package com.movieapp.domain.model;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class MovieScheduleTest {

    private final Movie movie = new Movie(1, "Film", "Desc", 100, "EN", "Dir", 10,
            LocalDate.of(2020, 1, 1), null, null);
    private final Theatre theatre = new Theatre(1, "UGC", "Marseille", "Rue A");

    @Test
    void create_valid_schedule_should_succeed() {
        MovieSchedule s = new MovieSchedule(1, movie, theatre,
                LocalDate.now(), LocalDate.now().plusDays(1),
                "MONDAY", LocalTime.of(18, 30), LocalDateTime.now());

        assertEquals("Film", s.getMovie().getTitle());
        assertEquals("UGC", s.getTheatre().getName());
    }

    @Test
    void create_schedule_with_invalid_dates_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new MovieSchedule(0, movie, theatre,
                        LocalDate.now(), LocalDate.now().minusDays(1),
                        "MONDAY", LocalTime.now(), null));
    }

    @Test
    void create_schedule_without_days_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new MovieSchedule(0, movie, theatre,
                        LocalDate.now(), LocalDate.now().plusDays(1),
                        "", LocalTime.now(), null));
    }
}
