package com.movieapp.api.rest.controller;

import com.movieapp.application.service.RoleService;
import com.movieapp.domain.model.Role;
import com.movieapp.api.rest.dto.request.CreateRoleRequest;
import com.movieapp.api.rest.dto.response.RoleResponse;
import com.movieapp.api.rest.mapper.RoleRestMapper;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .map(RoleRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public RoleResponse getById(@PathVariable int id) {
        return RoleRestMapper.toResponse(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody CreateRoleRequest request) {
        Role role = service.create(request.getName());
        return ResponseEntity.ok(RoleRestMapper.toResponse(role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable int id, @Valid @RequestBody CreateRoleRequest request) {
        Role updated = service.update(id, request.getName());
        return ResponseEntity.ok(RoleRestMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
