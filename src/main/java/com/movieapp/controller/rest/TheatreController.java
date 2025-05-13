package com.movieapp.controller.rest;

import com.movieapp.application.service.TheatreService;
import com.movieapp.domain.model.Theatre;
import com.movieapp.dto.request.CreateTheatreRequest;
import com.movieapp.dto.response.TheatreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    private final TheatreService service;

    public TheatreController(TheatreService service) {
        this.service = service;
    }

    @GetMapping
    public List<TheatreResponse> all() {
        return service.getAll().stream()
                .map(t -> new TheatreResponse(t.getId(), t.getName(), t.getCity(), t.getAddress()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TheatreResponse get(@PathVariable int id) {
        Theatre t = service.getById(id);
        return new TheatreResponse(t.getId(), t.getName(), t.getCity(), t.getAddress());
    }

    @PostMapping
    public ResponseEntity<TheatreResponse> create(@Valid @RequestBody CreateTheatreRequest req) {
        Theatre t = new Theatre(0, req.getName(), req.getCity(), req.getAddress());
        Theatre saved = service.create(t);
        return ResponseEntity.ok(new TheatreResponse(saved.getId(), saved.getName(), saved.getCity(), saved.getAddress()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
