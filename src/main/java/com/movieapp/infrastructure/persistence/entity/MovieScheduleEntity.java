package com.movieapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "movie_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private TheatreEntity theatre;

    private LocalDate startDate;
    private LocalDate endDate;
    private String daysOfWeek;
    private LocalTime time;
    private LocalDateTime createdAt;

    // === Audit automatique sur création uniquement ===
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
