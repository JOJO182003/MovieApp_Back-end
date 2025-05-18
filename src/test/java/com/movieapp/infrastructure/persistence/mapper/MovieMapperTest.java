package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.Movie;
import com.movieapp.infrastructure.persistence.entity.MovieEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    @Test
    void should_map_domain_to_entity_and_back() {
        Movie movie = new Movie(1, "Title", "Synopsis", 120, "EN", "Director", 12,
                LocalDate.of(2020, 1, 1), LocalDateTime.now(), LocalDateTime.now(), "test");

        MovieEntity entity = MovieMapper.toEntity(movie);
        Movie mapped = MovieMapper.toDomain(entity);

        assertEquals(movie.getTitle(), mapped.getTitle());
        assertEquals(movie.getDurationMinutes(), mapped.getDurationMinutes());
    }
}
