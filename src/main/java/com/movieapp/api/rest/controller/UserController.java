package com.movieapp.api.rest.controller;

import com.movieapp.application.service.UserService;
import com.movieapp.application.service.RoleService;
import com.movieapp.domain.model.User;
import com.movieapp.domain.model.Role;
import com.movieapp.api.rest.dto.request.CreateUserRequest;
import com.movieapp.api.rest.dto.response.UserResponse;
import com.movieapp.api.rest.mapper.UserRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UserResponse> all() {
        return userService.getAll().stream()
                .map(UserRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable int id) {
        return UserRestMapper.toResponse(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest req) {
        Role role = roleService.getById(req.getRoleId());
        String hashedPassword = passwordEncoder.encode(req.getPassword());

        User toCreate = UserRestMapper.fromRequest(req, role)
                .withHashedPassword(hashedPassword, LocalDateTime.now());

        User saved = userService.register(toCreate);
        return ResponseEntity.ok(UserRestMapper.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<Void> deleteByUsername(@PathVariable String username) {
        boolean deleted = userService.deleteByUsername(username);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
