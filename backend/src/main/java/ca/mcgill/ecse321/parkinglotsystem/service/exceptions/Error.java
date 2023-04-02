package ca.mcgill.ecse321.parkinglotsystem.service.exceptions;

import org.springframework.http.HttpStatus;

/**
 * The {@code Error} class is a DTO type for {@code ApplicationExceptionHandler}.
 */
public class Error {
    private final HttpStatus httpStatus;
    private final String message;

    public Error(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}