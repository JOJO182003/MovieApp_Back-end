package com.movieapp.controller.rest;

import com.movieapp.application.service.UserTheatreAssignmentService;
import com.movieapp.domain.model.UserTheatreAssignment;
import com.movieapp.dto.request.AssignUserToTheatreRequest;
import com.movieapp.dto.response.UserTheatreAssignmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
public class UserTheatreAssignmentController {

    private final UserTheatreAssignmentService service;

    public UserTheatreAssignmentController(UserTheatreAssignmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserTheatreAssignmentResponse> all() {
        return service.getAll().stream()
                .map(a -> new UserTheatreAssignmentResponse(
                        a.getUser().getId(),
                        a.getUser().getUsername(),
                        a.getTheatre().getId(),
                        a.getTheatre().getName(),
                        a.getAssignedAt()))
                .collect(Collectors.toList());
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
                .map(a -> new UserTheatreAssignmentResponse(
                        a.getUser().getId(),
                        a.getUser().getUsername(),
                        a.getTheatre().getId(),
                        a.getTheatre().getName(),
                        a.getAssignedAt()))
                .collect(Collectors.toList());
    }

    @GetMapping("/theatre/{theatreId}")
    public List<UserTheatreAssignmentResponse> byTheatre(@PathVariable int theatreId) {
        return service.getByTheatre(theatreId).stream()
                .map(a -> new UserTheatreAssignmentResponse(
                        a.getUser().getId(),
                        a.getUser().getUsername(),
                        a.getTheatre().getId(),
                        a.getTheatre().getName(),
                        a.getAssignedAt()))
                .collect(Collectors.toList());
    }
}
