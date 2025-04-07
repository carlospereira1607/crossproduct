package com.marketplace.crossproduct.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorWrapper> handleUserNotFound(DataNotFoundException ex) {
        ApiErrorWrapper error = new ApiErrorWrapper("ERR_DATA_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorWrapper> handleBadRequest(IllegalArgumentException ex) {
        ApiErrorWrapper error = new ApiErrorWrapper("ERR_BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorWrapper> handleRuntime(InvalidCredentialsException ex) {
        ApiErrorWrapper error = new ApiErrorWrapper("ERR_INVALID_CREDENTIALS", ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicatedEntryException.class)
    public ResponseEntity<ApiErrorWrapper> handleRuntime(DuplicatedEntryException ex) {
        ApiErrorWrapper error = new ApiErrorWrapper("ERR_DUPLICATED_DATA", ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorWrapper> handleRuntime(RuntimeException ex) {
        ApiErrorWrapper error = new ApiErrorWrapper("ERR_INTERNAL", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}