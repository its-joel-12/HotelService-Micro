package com.joel.rating.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RatingServiceException {
    private final Integer httpCode;
    private final HttpStatus httpStatus;
    private final String message;
    private final String description;
}
