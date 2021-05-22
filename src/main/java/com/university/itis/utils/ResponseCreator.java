package com.university.itis.utils;

import org.springframework.http.ResponseEntity;

public abstract class ResponseCreator {
    protected ResponseEntity createGoodResponse() {
        return ResponseEntity.ok().build();
    }

    protected <T> ResponseEntity<T> createGoodResponse(T body) {
        return ResponseEntity.ok(body);
    }

    protected ResponseEntity createErrorResponse(ErrorEntity errorEntity) {
        return ResponseEntity.status(errorEntity.getStatus()).body(errorEntity);
    }
}
