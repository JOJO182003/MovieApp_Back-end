package com.movieapp.controller.rest;

import com.movieapp.application.service.MovieService;
import com.movieapp.domain.model.Movie;
import com.movieapp.dto.request.CreateMovieRequest;
import com.movieapp.dto.response.MovieResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
                .map(m -> new MovieResponse(m.getId(), m.getTitle(), m.getLanguage(), m.getDirector(),
                        m.getDurationMinutes(), m.getReleaseDate()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MovieResponse get(@PathVariable int id) {
        Movie m = service.getById(id);
        return new MovieResponse(m.getId(), m.getTitle(), m.getLanguage(), m.getDirector(),
                m.getDurationMinutes(), m.getReleaseDate());
    }

    @PostMapping
    public ResponseEntity<MovieResponse> create(@Valid @RequestBody CreateMovieRequest req) {
        Movie movie = new Movie(0, req.getTitle(), req.getSynopsis(), req.getDurationMinutes(),
                req.getLanguage(), req.getDirector(), req.getMinAge(), req.getReleaseDate(),
                null, null);
        Movie saved = service.create(movie);
        return ResponseEntity.ok(new MovieResponse(saved.getId(), saved.getTitle(), saved.getLanguage(),
                saved.getDirector(), saved.getDurationMinutes(), saved.getReleaseDate()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
