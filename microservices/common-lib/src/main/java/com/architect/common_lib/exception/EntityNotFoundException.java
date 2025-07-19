package com.architect.common_lib.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public EntityNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
