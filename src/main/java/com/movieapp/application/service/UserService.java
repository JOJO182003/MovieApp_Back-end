package com.movieapp.application.service;

import com.movieapp.domain.model.User;
import com.movieapp.domain.repository.UserRepository;
import com.movieapp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(int id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non trouvé : " + id));
    }

    public User register(User user) {
        return repo.save(user);
    }

    public void delete(int id) {
        if (!repo.existsById(id)) throw new NotFoundException("ID inexistant");
        repo.deleteById(id);
    }

    public boolean deleteByUsername(String username) {
        return repo.findByUsername(username)
                .map(user -> {
                    repo.deleteById(user.getId());
                    return true;
                })
                .orElse(false);
    }

}
