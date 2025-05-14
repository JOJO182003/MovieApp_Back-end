package com.movieapp.util;

import com.movieapp.infrastructure.persistence.entity.MovieEntity;
import com.movieapp.infrastructure.persistence.entity.MovieScheduleEntity;
import com.movieapp.infrastructure.persistence.entity.TheatreEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EntityTestDataFactory {

    public static MovieEntity movieEntity1() {
        return MovieEntity.builder()
                .id(1)
                .title("Inception")
                .synopsis("A dream within a dream")
                .durationMinutes(148)
                .language("Anglais")
                .director("Christopher Nolan")
                .minAge(12)
                .releaseDate(LocalDate.of(2010, 7, 16))
                .createdAt(now())
                .updatedAt(now())
                .build();
    }

    public static TheatreEntity theatreEntity1() {
        return TheatreEntity.builder()
                .id(1)
                .name("Gaumont Opéra")
                .city("Paris")
                .address("32 avenue de l’Opéra, Paris")
                .build();
    }

    public static MovieScheduleEntity movieScheduleEntity1() {
        return MovieScheduleEntity.builder()
                .id(1)
                .movie(movieEntity1())
                .theatre(theatreEntity1())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .daysOfWeek("LUNDI,MERCREDI")
                .time(LocalTime.of(20, 30))
                .createdAt(now())
                .build();
    }

    private static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
