package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.domain.model.Movie;
import com.movieapp.infrastructure.persistence.entity.MovieEntity;
import com.movieapp.infrastructure.persistence.jpa.MovieJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieRepositoryAdapterTest {

    @Mock
    private MovieJpaRepository jpa;

    private MovieRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new MovieRepositoryAdapter(jpa);
    }

    @Test
    void findAll_should_return_domain_movies() {
        MovieEntity validEntity = MovieEntity.builder()
                .id(1)
                .title("Film Test")
                .synopsis("Synopsis")
                .durationMinutes(100)
                .language("Français")
                .director("Réalisateur")
                .minAge(12)
                .releaseDate(LocalDate.of(2023, 1, 1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(jpa.findAll()).thenReturn(List.of(validEntity));

        List<Movie> movies = adapter.findAll();
        assertEquals(1, movies.size());
        assertEquals("Film Test", movies.get(0).getTitle());
    }

    @Test
    void save_should_map_and_return() {
        Movie movie = new Movie(5, "Titre", "Synopsis", 120, "VO",
                "Nolan", 16, LocalDate.of(2020, 1, 1),
                LocalDateTime.now(), LocalDateTime.now());

        MovieEntity returnedEntity = MovieEntity.builder()
                .id(5)
                .title("Titre")
                .synopsis("Synopsis")
                .durationMinutes(120)
                .language("VO")
                .director("Nolan")
                .minAge(16)
                .releaseDate(LocalDate.of(2020, 1, 1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(jpa.save(any())).thenReturn(returnedEntity);

        Movie saved = adapter.save(movie);
        assertNotNull(saved);
        assertEquals("Titre", saved.getTitle());
    }
}
