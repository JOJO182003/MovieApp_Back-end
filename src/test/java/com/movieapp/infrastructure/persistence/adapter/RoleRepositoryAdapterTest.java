package com.movieapp.infrastructure.persistence.adapter;

import com.movieapp.domain.model.Role;
import com.movieapp.infrastructure.persistence.entity.RoleEntity;
import com.movieapp.infrastructure.persistence.springdata.RoleJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleRepositoryAdapterTest {

    @Mock
    private RoleJpaRepository jpa;

    private RoleRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        adapter = new RoleRepositoryAdapter(jpa);
    }

    @Test
    void findById_should_return_domain_role() {
        RoleEntity entity = RoleEntity.builder()
                .id(1)
                .name("admin")
                .build();

        when(jpa.findById(1)).thenReturn(Optional.of(entity));

        Optional<Role> result = adapter.findById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        assertEquals("admin", result.get().getName());
    }
}

