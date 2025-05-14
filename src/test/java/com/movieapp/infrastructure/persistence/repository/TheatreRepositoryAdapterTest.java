package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.domain.model.Theatre;
import com.movieapp.infrastructure.persistence.entity.TheatreEntity;
import com.movieapp.infrastructure.persistence.jpa.TheatreJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TheatreRepositoryAdapterTest {

    @Mock
    private TheatreJpaRepository jpa;

    private TheatreRepositoryAdapter adapter;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        adapter = new TheatreRepositoryAdapter(jpa);
    }

    @Test
    void findAll_should_return_mapped_theatres() {
        TheatreEntity entity = TheatreEntity.builder()
                .id(1)
                .name("UGC Toulouse")
                .city("Toulouse")
                .address("12 rue des Lilas")
                .build();

        when(jpa.findAll()).thenReturn(List.of(entity));

        List<Theatre> theatres = adapter.findAll();

        assertEquals(1, theatres.size());
        Theatre theatre = theatres.get(0);
        assertEquals(1, theatre.getId());
        assertEquals("UGC Toulouse", theatre.getName());
        assertEquals("Toulouse", theatre.getCity());
        assertEquals("12 rue des Lilas", theatre.getAddress());
    }
}
