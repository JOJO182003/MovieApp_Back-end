package com.movieapp.application.service;

import com.movieapp.domain.model.Role;
import com.movieapp.domain.repository.RoleRepository;
import com.movieapp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    public List<Role> getAllRoles() {
        return repo.findAll();
    }

    public Role getById(int id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Rôle non trouvé (id = " + id + ")"));
    }

    public Role create(String name) {
        return repo.save(new Role(0, name));
    }

    public Role update(int id, String newName) {
        Role role = getById(id).withName(newName);
        return repo.save(role);
    }

    public void delete(int id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Impossible de supprimer : rôle introuvable");
        }
        repo.deleteById(id);
    }
}
