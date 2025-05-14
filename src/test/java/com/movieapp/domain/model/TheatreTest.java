package com.movieapp.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TheatreTest {

    @Test
    void create_valid_theatre_should_succeed() {
        Theatre t = new Theatre(1, "Pathé", "Lyon", "8 rue cinéma");
        assertEquals("Pathé", t.getName());
    }

    @Test
    void create_theatre_without_name_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new Theatre(0, "", "Nice", "rue X"));
    }

    @Test
    void create_theatre_without_city_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new Theatre(0, "T", "", "rue"));
    }
}
