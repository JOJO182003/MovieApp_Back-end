package com.movieapp.api.rest.controller;

import com.movieapp.application.service.UserTheatreAssignmentService;
import com.movieapp.api.rest.dto.request.AssignUserToTheatreRequest;
import com.movieapp.api.rest.dto.response.UserTheatreAssignmentResponse;
import com.movieapp.api.rest.mapper.UserTheatreAssignmentRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/assignments")
public class UserTheatreAssignmentController {

    private final UserTheatreAssignmentService service;

    public UserTheatreAssignmentController(UserTheatreAssignmentService service) {
        this.service = service;
    }


    @GetMapping
    public List<UserTheatreAssignmentResponse> all() {
        return service.getAll().stream()
                .map(UserTheatreAssignmentRestMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<Void> assign(@Valid @RequestBody AssignUserToTheatreRequest request) {
        service.assignUserToTheatre(request.getUserId(), request.getTheatreId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int userId, @RequestParam int theatreId) {
        service.removeAssignment(userId, theatreId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public List<UserTheatreAssignmentResponse> byUser(@PathVariable int userId) {
        return service.getByUser(userId).stream()
                .map(UserTheatreAssignmentRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/theatre/{theatreId}")
    public List<UserTheatreAssignmentResponse> byTheatre(@PathVariable int theatreId) {
        return service.getByTheatre(theatreId).stream()
                .map(UserTheatreAssignmentRestMapper::toResponse)
                .toList();
    }
}
