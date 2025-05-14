package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.util.EntityTestDataFactory;

import com.movieapp.domain.model.MovieSchedule;
import com.movieapp.infrastructure.persistence.jpa.MovieScheduleJpaRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieScheduleRepositoryAdapterTest {

    @Mock
    private MovieScheduleJpaRepository jpa;

    private MovieScheduleRepositoryAdapter adapter;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        adapter = new MovieScheduleRepositoryAdapter(jpa);
    }

    @Test
    void findById_should_return_mapped() {
        when(jpa.findById(1)).thenReturn(Optional.of(EntityTestDataFactory.movieScheduleEntity1()));

        Optional<MovieSchedule> res = adapter.findById(1);

        assertTrue(res.isPresent());
        assertEquals(1, res.get().getId());
        assertEquals("Inception", res.get().getMovie().getTitle());
        assertEquals("Paris", res.get().getTheatre().getCity());
    }
}
