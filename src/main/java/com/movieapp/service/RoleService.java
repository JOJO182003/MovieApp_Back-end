package com.movieapp.service;

import com.movieapp.exception.ResourceNotFoundException;
import com.movieapp.model.Role;
import com.movieapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(int id) {
        return roleRepository.findById(id);
    }

    public Role getRoleByIdOrThrow(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rôle introuvable avec l'ID : " + id));
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(int id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : rôle inexistant (ID : " + id + ")");
        }
        roleRepository.deleteById(id);
    }
}
