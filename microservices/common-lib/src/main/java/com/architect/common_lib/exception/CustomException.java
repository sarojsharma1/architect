package com.architect.common_lib.exception;

public class CustomException extends Exception {
    public CustomException(String errorMessage) {
        super(errorMessage);
    }

    public CustomException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
