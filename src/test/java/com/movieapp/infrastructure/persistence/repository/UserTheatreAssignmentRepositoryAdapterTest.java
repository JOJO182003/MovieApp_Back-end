package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.domain.model.UserTheatreAssignment;
import com.movieapp.infrastructure.persistence.mapper.UserTheatreAssignmentMapper;
import com.movieapp.infrastructure.persistence.jpa.UserTheatreAssignmentJpaRepository;
import com.movieapp.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTheatreAssignmentRepositoryAdapterTest {

    @Mock
    private UserTheatreAssignmentJpaRepository jpa;

    private UserTheatreAssignmentRepositoryAdapter adapter;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        adapter = new UserTheatreAssignmentRepositoryAdapter(jpa);
    }

    @Test
    void findAll_should_return_assignments() {
        // Utilisation de la factory pour obtenir une instance de UserTheatreAssignment
        UserTheatreAssignment assignment = TestDataFactory.assignmentAdminLyon();

        // Simuler le comportement du JPA pour retourner une liste d'assignations
        when(jpa.findAll()).thenReturn(List.of(UserTheatreAssignmentMapper.toEntity(assignment)));

        // Appel de la méthode findAll() de l'adaptateur
        List<UserTheatreAssignment> assignments = adapter.findAll();

        // Vérification des résultats
        assertEquals(1, assignments.size());  // On s'attend à une seule assignation
        assertNotNull(assignments.get(0).getUser());  // Vérifie que l'utilisateur a été mappé correctement
        assertNotNull(assignments.get(0).getTheatre());  // Vérifie que le théâtre a été mappé correctement
        assertNotNull(assignments.get(0).getAssignedAt());  // Vérifie que la date d'assignation est mappée correctement
    }
}


