package com.joel.user.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class UserServiceException {
    private final Integer httpCode;
    private final HttpStatus httpStatus;
    private final String message;
    private final String description;
}
