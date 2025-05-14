package com.movieapp.api.rest.controller;

import com.movieapp.application.service.MovieService;
import com.movieapp.domain.model.Movie;
import com.movieapp.api.rest.dto.request.CreateMovieRequest;
import com.movieapp.api.rest.dto.response.MovieResponse;
import com.movieapp.api.rest.mapper.MovieRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieResponse> all() {
        return service.getAll().stream()
                .map(MovieRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public MovieResponse get(@PathVariable int id) {
        return MovieRestMapper.toResponse(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponse> create(@Valid @RequestBody CreateMovieRequest req) {
        Movie toCreate = MovieRestMapper.fromRequest(req);
        Movie saved = service.create(toCreate);
        return ResponseEntity.ok(MovieRestMapper.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
