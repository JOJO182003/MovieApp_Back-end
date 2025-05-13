package com.movieapp.application.service;

import com.movieapp.domain.model.Movie;
import com.movieapp.domain.repository.MovieRepository;
import com.movieapp.exception.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static com.movieapp.util.TestDataFactory.*;
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
    void getById_should_return_movie_when_found() {
        Movie expected = movieInception();

        when(movieRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        Movie result = movieService.getById(expected.getId());

        assertNotNull(result);
        assertEquals(expected.getTitle(), result.getTitle());
    }

    @Test
    void getById_should_throw_when_not_found() {
        when(movieRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> movieService.getById(999));
    }

    @Test
    void create_should_delegate_to_repository() {
        Movie movie = movieFrenchDrama();

        movieService.create(movie);

        verify(movieRepository).save(movie);
    }
}
