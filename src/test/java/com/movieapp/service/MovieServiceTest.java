package com.movieapp.service;

import com.movieapp.exception.ResourceNotFoundException;
import com.movieapp.model.Movie;
import com.movieapp.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_withRealisticData() {
        Movie m1 = Movie.builder()
                .id(1)
                .title("Film 1")
                .language("Allemand")
                .durationMinutes(87)
                .releaseDate(LocalDate.of(2006, 1, 20))
                .build();

        Movie m2 = Movie.builder()
                .id(2)
                .title("Film 2")
                .language("Allemand")
                .durationMinutes(130)
                .releaseDate(LocalDate.of(2014, 4, 24))
                .build();

        when(movieRepository.findAll()).thenReturn(List.of(m1, m2));

        List<Movie> result = movieService.getAllMovies();

        assertEquals(2, result.size());

        Movie first = result.get(0);
        assertEquals("Film 1", first.getTitle());
        assertEquals(87, first.getDurationMinutes());
        assertEquals("Allemand", first.getLanguage());
        assertEquals(LocalDate.of(2006, 1, 20), first.getReleaseDate());

        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Found() {
        Movie movie = new Movie();
        movie.setId(1);
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.getMovieById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(movieRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(movieRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Movie> result = movieService.getMovieById(99);

        assertFalse(result.isPresent());
        verify(movieRepository, times(1)).findById(99);
    }


    @Test
    void testSave() {
        Movie movie = new Movie();
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.saveMovie(movie);

        assertNotNull(result);
        assertEquals(movie, result);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void testDeleteMovie_Found() {
        // On simule que le film avec l'ID 1 existe
        when(movieRepository.existsById(1)).thenReturn(true);

        // On appelle la méthode à tester
        movieService.deleteMovie(1);

        // On vérifie que les bonnes méthodes ont été appelées
        verify(movieRepository).existsById(1);
        verify(movieRepository).deleteById(1);
    }

    @Test
    void testDeleteMovie_NotFound() {
        // On simule que le film avec l'ID 1 n'existe pas
        when(movieRepository.existsById(1)).thenReturn(false);

        // On vérifie que la méthode lance bien l'exception attendue
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> movieService.deleteMovie(1)
        );

        // Optionnel : on vérifie le message d'erreur
        assertTrue(thrown.getMessage().contains("aucun film avec l'ID"));

        // On vérifie que la suppression n’a jamais été appelée
        verify(movieRepository, never()).deleteById(anyInt());
    }

}
