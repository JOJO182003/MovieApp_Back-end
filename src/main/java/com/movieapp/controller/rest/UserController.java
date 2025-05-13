package com.movieapp.controller.rest;

import com.movieapp.application.service.UserService;
import com.movieapp.domain.model.User;
import com.movieapp.dto.request.CreateUserRequest;
import com.movieapp.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponse> all() {
        return service.getAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable int id) {
        User u = service.getById(id);
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole().getName());
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest req) {
        // ici tu devras appeler un PasswordEncoder et chercher le rôle via RoleService
        throw new UnsupportedOperationException("À compléter avec hash et rôle");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
