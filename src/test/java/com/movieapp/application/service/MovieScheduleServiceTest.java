package com.movieapp.application.service;

import com.movieapp.domain.model.MovieSchedule;
import com.movieapp.domain.repository.MovieScheduleRepository;
import com.movieapp.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static com.movieapp.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieScheduleServiceTest {

    @Mock
    private MovieScheduleRepository repository;

    @InjectMocks
    private MovieScheduleService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_should_return_projection_when_found() {
        MovieSchedule expected = scheduleInceptionMarseille();

        when(repository.findById(1001)).thenReturn(Optional.of(expected));

        MovieSchedule result = service.getById(1001);
        assertEquals(expected, result);
    }

    @Test
    void getById_should_throw_when_not_found() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(999));
    }

    @Test
    void create_should_call_repository_save() {
        MovieSchedule schedule = scheduleFuturePastBdx();

        service.create(schedule);

        verify(repository).save(schedule);
    }
}
