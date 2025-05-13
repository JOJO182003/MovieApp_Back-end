package com.movieapp.exception;

/**
 * Exception utilisée quand un utilisateur tente un accès non autorisé.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
