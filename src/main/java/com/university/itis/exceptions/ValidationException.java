package com.university.itis.exceptions;

import com.university.itis.utils.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private final ErrorEntity error;
}
