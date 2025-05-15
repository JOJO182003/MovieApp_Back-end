package com.movieapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theatres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String city;
    private String address;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MovieScheduleEntity> schedules = new ArrayList<>();

    @ManyToMany(mappedBy = "theatres")
    @Builder.Default
    private List<UserEntity> users = new ArrayList<>();
}
