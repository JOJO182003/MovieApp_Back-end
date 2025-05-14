package com.movieapp.exception;

/**
 * Exception métier pour signaler une validation manuelle échouée.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
