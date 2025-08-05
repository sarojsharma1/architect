package com.architect.common_lib.exception;

import com.architect.common_lib.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ResponseDto responseDto = new ResponseDto(ex.getMessage(), null);
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
}
