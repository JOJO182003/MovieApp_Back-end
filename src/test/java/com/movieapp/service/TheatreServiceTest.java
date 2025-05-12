package com.movieapp.service;

import com.movieapp.exception.ResourceNotFoundException;
import com.movieapp.model.Theatre;
import com.movieapp.repository.TheatreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TheatreServiceTest {

    @Mock
    private TheatreRepository theatreRepository;

    @InjectMocks
    private TheatreService theatreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(theatreRepository.findAll()).thenReturn(Collections.emptyList());
        List<Theatre> result = theatreService.getAllTheatres();
        assertNotNull(result);
        verify(theatreRepository).findAll();
    }

    @Test
    void testFindById() {
        Theatre obj = new Theatre();
        obj.setId(1);
        when(theatreRepository.findById(1)).thenReturn(Optional.of(obj));
        Optional<Theatre> result = theatreService.getTheatreById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testSave() {
        Theatre obj = new Theatre();
        when(theatreRepository.save(obj)).thenReturn(obj);
        Theatre result = theatreService.saveTheatre(obj);
        assertEquals(obj, result);
    }

    @Test
    void testDeleteTheatre_Found() {
        when(theatreRepository.existsById(1)).thenReturn(true);

        theatreService.deleteTheatre(1);

        verify(theatreRepository).existsById(1);
        verify(theatreRepository).deleteById(1);
    }

    @Test
    void testDeleteTheatre_NotFound() {
        when(theatreRepository.existsById(1)).thenReturn(false);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> theatreService.deleteTheatre(1)
        );

        assertTrue(thrown.getMessage().contains("théâtre non trouvé")); // correspond au vrai message
        verify(theatreRepository, never()).deleteById(anyInt());
    }
}
