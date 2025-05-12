package com.movieapp.service;

import com.movieapp.exception.ResourceNotFoundException;
import com.movieapp.model.MovieTheatre;
import com.movieapp.repository.MovieTheatreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieTheatreServiceTest {

    @Mock
    private MovieTheatreRepository movieTheatreRepository;

    @InjectMocks
    private MovieTheatreService movieTheatreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(movieTheatreRepository.findAll()).thenReturn(Collections.emptyList());
        List<MovieTheatre> result = movieTheatreService.getAllProjections();
        assertNotNull(result);
        verify(movieTheatreRepository).findAll();
    }

    @Test
    void testFindById_Found() {
        MovieTheatre theatre = new MovieTheatre();
        theatre.setId(1);

        when(movieTheatreRepository.findById(1)).thenReturn(Optional.of(theatre));

        Optional<MovieTheatre> result = movieTheatreService.getProjectionById(1); // or getMovieTheatreById()

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(movieTheatreRepository, times(1)).findById(1);
    }


    @Test
    void testSave() {
        MovieTheatre obj = new MovieTheatre();
        when(movieTheatreRepository.save(obj)).thenReturn(obj);
        MovieTheatre result = movieTheatreService.saveProjection(obj);
        assertEquals(obj, result);
    }

    @Test
    void testDelete() {
        when(movieTheatreRepository.existsById(1)).thenReturn(true);

        movieTheatreService.deleteProjection(1);

        verify(movieTheatreRepository).existsById(1);
        verify(movieTheatreRepository).deleteById(1);
    }

    @Test
    void testDelete_NotFound() {
        when(movieTheatreRepository.existsById(1)).thenReturn(false);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> movieTheatreService.deleteProjection(1)
        );

        assertTrue(thrown.getMessage().contains("projection non trouvée"));
        verify(movieTheatreRepository, never()).deleteById(anyInt());
    }

}
