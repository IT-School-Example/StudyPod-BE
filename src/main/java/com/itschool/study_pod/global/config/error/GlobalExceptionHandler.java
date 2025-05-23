package com.itschool.study_pod.global.config.error;

import com.itschool.study_pod.global.base.dto.Header;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Header<Void>> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Header.ERROR("EntityNotFoundException : " + ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Header<Void>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Header.ERROR("Unhandled exception: " + ex.getMessage()));
    }
}
