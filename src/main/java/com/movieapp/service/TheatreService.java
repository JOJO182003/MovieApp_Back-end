package com.movieapp.service;

import com.movieapp.exception.ResourceNotFoundException;
import com.movieapp.model.Theatre;
import com.movieapp.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheatreService {

    private final TheatreRepository theatreRepository;

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public Optional<Theatre> getTheatreById(int id) {
        return theatreRepository.findById(id);
    }

    public Theatre getTheatreByIdOrThrow(int id) {
        return theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Théâtre introuvable avec l'ID : " + id));
    }

    public Theatre saveTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    public void deleteTheatre(int id) {
        if (!theatreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : théâtre non trouvé (ID : " + id + ")");
        }
        theatreRepository.deleteById(id);
    }
}
