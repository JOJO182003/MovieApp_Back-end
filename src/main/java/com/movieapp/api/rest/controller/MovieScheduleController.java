package com.movieapp.api.rest.controller;

import com.movieapp.application.service.*;
import com.movieapp.domain.model.*;
import com.movieapp.api.rest.dto.request.CreateMovieScheduleRequest;
import com.movieapp.api.rest.dto.response.MovieScheduleResponse;
import com.movieapp.api.rest.mapper.MovieScheduleRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class MovieScheduleController {

    private final MovieScheduleService service;
    private final MovieService movieService;
    private final TheatreService theatreService;

    public MovieScheduleController(MovieScheduleService service, MovieService movieService, TheatreService theatreService) {
        this.service = service;
        this.movieService = movieService;
        this.theatreService = theatreService;
    }

    @GetMapping
    public List<MovieScheduleResponse> all() {
        return service.getAll().stream()
                .map(MovieScheduleRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/get/{id}")
    public List<MovieScheduleResponse> get(@PathVariable int id) {
        return service.findByMovieId(id).stream()
                .map(MovieScheduleRestMapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasRole('CINEMA_OWNER')")
    @PostMapping
    public ResponseEntity<MovieScheduleResponse> create(@Valid @RequestBody CreateMovieScheduleRequest req) {
        Movie movie = movieService.getById(req.getMovieId());
        Theatre theatre = theatreService.getById(req.getTheatreId());

        MovieSchedule toCreate = MovieScheduleRestMapper.fromRequest(req, movie, theatre);
        MovieSchedule saved = service.create(toCreate);

        return ResponseEntity.ok(MovieScheduleRestMapper.toResponse(saved));
    }

    @PreAuthorize("hasRole('CINEMA_OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
