package com.movieapp.service;

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
    void testFindById() {
        MovieTheatre obj = new MovieTheatre();
        obj.setId(1);
        when(movieTheatreRepository.findById(1)).thenReturn(Optional.of(obj));
        Optional<Object> result = Optional.ofNullable(movieTheatreService.getProjectionById(1));
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getClass());
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
        movieTheatreService.deleteProjection(1);
        verify(movieTheatreRepository).deleteById(1);
    }
}
