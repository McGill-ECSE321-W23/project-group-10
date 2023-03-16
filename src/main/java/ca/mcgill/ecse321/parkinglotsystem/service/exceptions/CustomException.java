package ca.mcgill.ecse321.parkinglotsystem.service.exceptions;

import org.springframework.http.HttpStatus;

/**
 * {@code CustomException} is a subclass of {@code RuntimeException} that specifies
 * the message and HTTP status to be sent back after an unsuccessful request.
 */
public class CustomException extends RuntimeException{
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
