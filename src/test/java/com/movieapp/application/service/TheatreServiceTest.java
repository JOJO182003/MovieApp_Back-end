package com.movieapp.application.service;

import com.movieapp.domain.model.Theatre;
import com.movieapp.domain.repository.TheatreRepository;
import com.movieapp.exception.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static com.movieapp.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TheatreServiceTest {

    @Mock
    private TheatreRepository theatreRepository;

    @InjectMocks
    private TheatreService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_should_return_theatre_when_found() {
        Theatre expected = theatreLille8();

        when(theatreRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        Theatre result = service.getById(expected.getId());
        assertEquals(expected, result);
    }

    @Test
    void getById_should_throw_when_not_found() {
        when(theatreRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(999));
    }
}
