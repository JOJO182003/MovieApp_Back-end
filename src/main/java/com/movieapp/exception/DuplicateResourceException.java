package com.movieapp.exception;

/**
 * Exception levée lorsqu'une ressource existe déjà (ex : doublon d'email).
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
