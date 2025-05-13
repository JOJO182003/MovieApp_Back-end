package com.movieapp.application.service;

import com.movieapp.domain.model.User;
import com.movieapp.domain.repository.UserRepository;
import com.movieapp.exception.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static com.movieapp.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_should_return_user_when_found() {
        User expected = adminUser();

        when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        User result = userService.getById(expected.getId());
        assertEquals(expected, result);
    }

    @Test
    void getById_should_throw_when_absent() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(42));
    }

    @Test
    void register_should_delegate_to_repository() {
        User user = standardUser();

        userService.register(user);

        verify(userRepository).save(user);
    }
}
