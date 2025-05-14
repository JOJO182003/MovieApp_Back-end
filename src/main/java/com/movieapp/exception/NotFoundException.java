package com.movieapp.exception;

/**
 * Exception standard levée lorsqu'une ressource est introuvable.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
