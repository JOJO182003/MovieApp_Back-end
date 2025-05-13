package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.domain.model.User;
import com.movieapp.infrastructure.persistence.entity.UserEntity;
import com.movieapp.infrastructure.persistence.jpa.UserJpaRepository;
import com.movieapp.infrastructure.persistence.mapper.UserMapper;
import com.movieapp.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository jpa;

    private UserRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new UserRepositoryAdapter(jpa);
    }

    @Test
    void findAll_should_return_domain_users() {
        when(jpa.findAll()).thenReturn(List.of(TestDataFactory.ownerUserEntity()));

        List<User> users = adapter.findAll();

        assertEquals(1, users.size());
        assertEquals("owner1", users.get(0).getUsername());
    }

    @Test
    void findById_should_return_user_if_exists() {
        when(jpa.findById(34)).thenReturn(Optional.of(TestDataFactory.ownerUserEntity()));

        Optional<User> user = adapter.findById(34);

        assertTrue(user.isPresent());
        assertEquals("owner1", user.get().getUsername());
    }

    @Test
    void findByUsername_should_return_user_if_exists() {
        when(jpa.findByUsername("owner1")).thenReturn(Optional.of(TestDataFactory.ownerUserEntity()));

        Optional<User> user = adapter.findByUsername("owner1");

        assertTrue(user.isPresent());
        assertEquals("owner1", user.get().getUsername());
    }

    @Test
    void findByEmail_should_return_user_if_exists() {
        when(jpa.findByEmail("owner1@movieapp.com")).thenReturn(Optional.of(TestDataFactory.ownerUserEntity()));

        Optional<User> user = adapter.findByEmail("owner1@movieapp.com");

        assertTrue(user.isPresent());
        assertEquals("owner1@movieapp.com", user.get().getEmail());
    }

    @Test
    void save_should_map_and_return_saved_user() {
        User user = UserMapper.toDomain(TestDataFactory.ownerUserEntity()); // domaine ✔
        UserEntity entity = TestDataFactory.ownerUserEntity(); // persistence

        when(jpa.save(any())).thenReturn(entity);

        User saved = adapter.save(user);

        assertNotNull(saved);
        assertEquals("owner1", saved.getUsername());
    }


    @Test
    void deleteById_should_call_jpa_delete() {
        adapter.deleteById(31);
        verify(jpa, times(1)).deleteById(31);
    }

    @Test
    void existsById_should_return_true_if_exists() {
        when(jpa.existsById(31)).thenReturn(true);
        assertTrue(adapter.existsById(31));
    }
}
