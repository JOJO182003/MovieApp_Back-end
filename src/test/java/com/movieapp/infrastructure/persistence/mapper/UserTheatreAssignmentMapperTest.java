package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.*;
import com.movieapp.infrastructure.persistence.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTheatreAssignmentMapperTest {

    @Test
    void should_map_assignment_both_ways() {
        User user = new User(1, "john", "john@example.com", "hash", new Role(1, "USER"), null, LocalDateTime.now(), LocalDateTime.now());
        Theatre theatre = new Theatre(2, "Ciné", "Lyon", "Rue A");
        UserTheatreAssignment domain = new UserTheatreAssignment(user, theatre, LocalDateTime.now());

        UserTheatreAssignmentEntity entity = UserTheatreAssignmentMapper.toEntity(domain);
        UserTheatreAssignment mapped = UserTheatreAssignmentMapper.toDomain(entity);

        assertEquals(domain.getUser().getId(), mapped.getUser().getId());
        assertEquals(domain.getTheatre().getName(), mapped.getTheatre().getName());
    }
}
