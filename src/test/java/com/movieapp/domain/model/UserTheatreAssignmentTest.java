package com.movieapp.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.movieapp.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTheatreAssignmentTest {

    private final User user = adminUser();
    private final Theatre theatre = theatreLyon1();

    @Test
    void create_valid_assignment_should_succeed() {
        LocalDateTime now = LocalDateTime.now();
        UserTheatreAssignment assignment = new UserTheatreAssignment(user, theatre, now);

        assertEquals(user.getId(), assignment.getUser().getId());
        assertEquals(theatre.getId(), assignment.getTheatre().getId());
        assertEquals(now, assignment.getAssignedAt());
    }

    @Test
    void create_assignment_without_user_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new UserTheatreAssignment(null, theatre, LocalDateTime.now()));
    }

    @Test
    void create_assignment_without_theatre_should_fail() {
        assertThrows(IllegalArgumentException.class, () ->
                new UserTheatreAssignment(user, null, LocalDateTime.now()));
    }

    @Test
    void create_assignment_with_null_date_should_set_now() {
        UserTheatreAssignment assignment = new UserTheatreAssignment(user, theatre, null);
        assertNotNull(assignment.getAssignedAt());
    }
}
