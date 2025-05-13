package com.movieapp.infrastructure.persistence.adapter;

import com.movieapp.domain.model.UserTheatreAssignment;
import com.movieapp.infrastructure.persistence.mapper.UserTheatreAssignmentMapper;
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
        // Utilisation de la factory pour obtenir une entité existante
        UserTheatreAssignment assignment = TestDataFactory.assignmentAdminLyon(); // Utilisation d'une méthode de la factory

        // Simuler le comportement du JPA pour retourner une liste d'assignations
        when(jpa.All()).thenReturn(List.of(UserTheatreAssignmentMapper.toEntity(assignment)));

        // Appel de la méthode findAll() de l'adaptateur
        List<UserTheatreAssignment> assignments = adapter.findAll();

        // Vérification des résultats
        assertEquals(1, assignments.size());
        assertNotNull(assignments.get(0).getUser());  // Vérifier que l'utilisateur est bien mappé
        assertNotNull(assignments.get(0).getTheatre());  // Vérifier que le théâtre est bien mappé
        assertNotNull(assignments.get(0).getAssignedAt());  // Vérifier que la date est bien mappée
    }
}
