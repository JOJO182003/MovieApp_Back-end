package com.movieapp.exception;

/**
 * Exception générique pour exprimer une règle métier violée.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
