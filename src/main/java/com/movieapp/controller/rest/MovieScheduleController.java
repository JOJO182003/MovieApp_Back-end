package com.movieapp.controller.rest;

import com.movieapp.application.service.MovieScheduleService;
import com.movieapp.domain.model.MovieSchedule;
import com.movieapp.dto.request.CreateMovieScheduleRequest;
import com.movieapp.dto.response.MovieScheduleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedules")
public class MovieScheduleController {

    private final MovieScheduleService service;

    public MovieScheduleController(MovieScheduleService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieScheduleResponse> all() {
        return service.getAll().stream()
                .map(s -> new MovieScheduleResponse(s.getId(), s.getMovie().getTitle(),
                        s.getTheatre().getName(), s.getStartDate(), s.getEndDate(),
                        s.getDaysOfWeek(), s.getTime()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MovieScheduleResponse get(@PathVariable int id) {
        MovieSchedule s = service.getById(id);
        return new MovieScheduleResponse(s.getId(), s.getMovie().getTitle(),
                s.getTheatre().getName(), s.getStartDate(), s.getEndDate(),
                s.getDaysOfWeek(), s.getTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // POST omitted : nécessite un mapping movie/theatre depuis ID -> entités
}
