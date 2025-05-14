package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.*;
import com.movieapp.infrastructure.persistence.entity.*;
import org.junit.jupiter.api.Test;

import java.time.*;

import static com.movieapp.util.TestDataFactory.movieInception;
import static com.movieapp.util.TestDataFactory.theatreMarseille3;
import static org.junit.jupiter.api.Assertions.*;

class MovieScheduleMapperTest {

    @Test
    void should_map_entity_to_domain_and_back() {
        Movie movie = movieInception();
        Theatre theatre = theatreMarseille3();

        MovieSchedule domain = new MovieSchedule(
                999,
                movie,
                theatre,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 15),
                "Lundi,Mardi",
                LocalTime.of(20, 0),
                LocalDateTime.now()
        );

        MovieScheduleEntity entity = MovieScheduleMapper.toEntity(domain);
        MovieSchedule mappedBack = MovieScheduleMapper.toDomain(entity);

        assertEquals(domain.getId(), mappedBack.getId());
        assertEquals(domain.getMovie().getTitle(), mappedBack.getMovie().getTitle());
        assertEquals(domain.getTheatre().getCity(), mappedBack.getTheatre().getCity());
    }

}
