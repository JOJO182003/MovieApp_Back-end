package com.movieapp.api.rest.controller;

import com.movieapp.application.service.TheatreService;
import com.movieapp.domain.model.Theatre;
import com.movieapp.api.rest.dto.request.CreateTheatreRequest;
import com.movieapp.api.rest.dto.response.TheatreResponse;
import com.movieapp.api.rest.mapper.TheatreRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .map(TheatreRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TheatreResponse get(@PathVariable int id) {
        return TheatreRestMapper.toResponse(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<TheatreResponse> create(@Valid @RequestBody CreateTheatreRequest req) {
        Theatre theatre = TheatreRestMapper.fromRequest(req);
        Theatre saved = service.create(theatre);
        return ResponseEntity.ok(TheatreRestMapper.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
