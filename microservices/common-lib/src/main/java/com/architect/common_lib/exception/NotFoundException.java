package com.architect.common_lib.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
