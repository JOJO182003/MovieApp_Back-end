package com.movieapp.service;

import com.movieapp.exception.ResourceNotFoundException;
import com.movieapp.model.Role;
import com.movieapp.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());
        List<Role> result = roleService.getAllRoles();
        assertNotNull(result);
        verify(roleRepository).findAll();
    }

    @Test
    void testFindById() {
        Role obj = new Role();
        obj.setId(1);
        when(roleRepository.findById(1)).thenReturn(Optional.of(obj));
        Optional<Role> result = roleService.getRoleById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testSave() {
        Role obj = new Role();
        when(roleRepository.save(obj)).thenReturn(obj);
        Role result = roleService.saveRole(obj);
        assertEquals(obj, result);
    }

    @Test
    void testDeleteRole_Found() {
        when(roleRepository.existsById(1)).thenReturn(true);

        roleService.deleteRole(1);

        verify(roleRepository).existsById(1);
        verify(roleRepository).deleteById(1);
    }

    @Test
    void testDeleteRole_NotFound() {
        when(roleRepository.existsById(1)).thenReturn(false);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> roleService.deleteRole(1)
        );

        assertTrue(thrown.getMessage().contains("rôle inexistant"));
        verify(roleRepository, never()).deleteById(anyInt());
    }
}
