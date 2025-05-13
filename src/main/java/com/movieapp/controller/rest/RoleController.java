package com.movieapp.controller.rest;

import com.movieapp.application.service.RoleService;
import com.movieapp.domain.model.Role;
import com.movieapp.dto.request.CreateRoleRequest;
import com.movieapp.dto.response.RoleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<RoleResponse> getAll() {
        return service.getAllRoles().stream()
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RoleResponse getById(@PathVariable int id) {
        Role r = service.getById(id);
        return new RoleResponse(r.getId(), r.getName());
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody CreateRoleRequest request) {
        Role r = service.create(request.getName());
        return ResponseEntity.ok(new RoleResponse(r.getId(), r.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable int id, @Valid @RequestBody CreateRoleRequest request) {
        Role r = service.update(id, request.getName());
        return ResponseEntity.ok(new RoleResponse(r.getId(), r.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
