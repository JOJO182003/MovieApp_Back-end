package com.movieapp.application.service;

import com.movieapp.domain.model.Role;
import com.movieapp.domain.repository.RoleRepository;
import com.movieapp.exception.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static com.movieapp.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_should_return_role_when_found() {
        Role expected = roleAdmin();

        when(roleRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        Role result = service.getById(expected.getId());
        assertEquals(expected, result);
    }

    @Test
    void getById_should_throw_when_not_found() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(999));
    }

    @Test
    void create_should_return_saved_role() {
        Role saved = new Role(0, "MODERATOR");

        when(roleRepository.save(any(Role.class))).thenReturn(saved);

        Role result = service.create("MODERATOR");

        assertEquals("MODERATOR", result.getName());
        verify(roleRepository).save(any(Role.class));
    }

    @Captor
    private ArgumentCaptor<Role> roleCaptor;

    @Test
    void update_should_call_save_with_new_name() {
        // Given
        Role existing = new Role(3, "moderator");
        when(roleRepository.findById(3)).thenReturn(Optional.of(existing));

        // When
        service.update(3, "new-name");

        // Then
        verify(roleRepository).save(roleCaptor.capture());
        Role saved = roleCaptor.getValue();
        assertEquals("new-name", saved.getName());
        assertEquals(3, saved.getId());
    }


    @Test
    void delete_should_call_delete_if_exists() {
        when(roleRepository.existsById(5)).thenReturn(true);

        service.delete(5);

        verify(roleRepository).deleteById(5);
    }

    @Test
    void delete_should_throw_if_not_found() {
        when(roleRepository.existsById(6)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.delete(6));
    }
}
