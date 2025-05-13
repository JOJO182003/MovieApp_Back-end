package com.movieapp.infrastructure.persistence.adapter;

import com.movieapp.domain.model.User;
import com.movieapp.infrastructure.persistence.entity.UserEntity;
import com.movieapp.infrastructure.persistence.springdata.UserJpaRepository;
import com.movieapp.util.TestDataFactory;  // Assure-toi que la factory est bien importée
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository jpa;

    private UserRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        adapter = new UserRepositoryAdapter(jpa);
    }

    @Test
    void findByUsername_should_return_user() {
        // Utilisation de la TestDataFactory pour créer un UserEntity simulé
        UserEntity entity = TestDataFactory.adminUserEntity();  // Exemple d'utilisation de la factory pour créer un UserEntity

        // Simulation du retour de findByUsername
        when(jpa.findByUsername("admin1")).thenReturn(Optional.of(entity));

        // Appel de la méthode du repository
        Optional<User> result = adapter.findByUsername("admin1");

        // Vérification que l'utilisateur est présent
        assertTrue(result.isPresent());

        // Vérification des données mappées correctement
        User user = result.get();
        assertEquals(31, user.getId());  // Utilise les valeurs définies dans la factory
        assertEquals("admin1", user.getUsername());
        assertEquals("admin1@movieapp.com", user.getEmail());
        assertEquals("hashed_admin", user.getPasswordHash());
    }
}

