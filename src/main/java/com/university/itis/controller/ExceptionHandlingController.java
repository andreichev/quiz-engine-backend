package com.university.itis.controller;

import com.university.itis.exceptions.ValidationException;
import com.university.itis.utils.ErrorEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorEntity> validationExceptionHandler(ValidationException exception) {
        ErrorEntity error = exception.getError();
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
