package com.movieapp.service;

import com.movieapp.model.User;
import com.movieapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<User> result = userService.getAllUsers();
        assertNotNull(result);
        verify(userRepository).findAll();
    }

    @Test
    void testFindById() {
        User obj = new User();
        obj.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(obj));
        Optional<User> result = userService.getUserById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testSave() {
        User obj = new User();
        when(userRepository.save(obj)).thenReturn(obj);
        User result = userService.saveUser(obj);
        assertEquals(obj, result);
    }

    @Test
    void testDelete() {
        userService.deleteUser(1);
        verify(userRepository).deleteById(1);
    }
}
