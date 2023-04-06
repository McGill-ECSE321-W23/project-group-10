package ca.mcgill.ecse321.parkinglotsystem.service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    /**
     * Handles CustomException.
     * @param e the exception to be handled
     * @return The response entity with the given message and HTTP status
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Error> handleException(CustomException e) {
        Error error = new Error(e.getMessage(), e.getHttpStatus());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }
    
}
