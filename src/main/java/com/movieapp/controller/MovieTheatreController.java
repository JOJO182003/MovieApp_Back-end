package com.movieapp.controller;

import com.movieapp.model.MovieTheatre;
import com.movieapp.service.MovieTheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projections")
@RequiredArgsConstructor
public class MovieTheatreController {

    private final MovieTheatreService movieTheatreService;

    @GetMapping
    public List<MovieTheatre> getAllProjections() {
        return movieTheatreService.getAllProjections();
    }

    @GetMapping("/find-all")
    public List<MovieTheatre> findAllProjections() {
        return movieTheatreService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieTheatre> getProjectionById(@PathVariable int id) {
        return movieTheatreService.getProjectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/strict/{id}")
    public MovieTheatre getProjectionStrict(@PathVariable int id) {
        return movieTheatreService.getProjectionByIdOrThrow(id);
    }

    @GetMapping("/city/{city}")
    public List<MovieTheatre> getProjectionsByCity(@PathVariable String city) {
        return movieTheatreService.findByCity(city);
    }

    @PostMapping
    public MovieTheatre saveProjection(@RequestBody MovieTheatre projection) {
        return movieTheatreService.saveProjection(projection);
    }

    @PostMapping("/raw")
    public MovieTheatre saveRaw(@RequestBody MovieTheatre mt) {
        return movieTheatreService.save(mt);
    }

    @DeleteMapping("/{id}")
    public void deleteProjection(@PathVariable int id) {
        movieTheatreService.deleteProjection(id);
    }
}
