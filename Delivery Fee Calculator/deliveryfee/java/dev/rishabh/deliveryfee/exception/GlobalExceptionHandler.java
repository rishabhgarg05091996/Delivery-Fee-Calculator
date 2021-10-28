/**
 * This is a Global Exception Handler used for handling different type of Exceptions.
 * 
 * @author Rishabh Garg
 */
package dev.rishabh.deliveryfee.exception;

import java.time.format.DateTimeParseException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handling specific exception for Bad Request Input
     * 
     * @param exception - Instance of ResourceNotFoundException class
     * @param request - WebService request
     * @return - Response Entity with Error Details and Status.
     */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

    /**
     * Handling specific exception for Bad Request Input for Invalid Data
     * 
     * @param exception - Instance of HttpMessageNotReadableException class
     * @param request - WebService request
     * @return - Response Entity with Error Details and Status.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> numberFormatHandling(HttpMessageNotReadableException exception, WebRequest request) {
        ErrorDetails errorDetails = 
            new ErrorDetails(new Date(), "Invalid Data, Kindly verify the data!", 
                                request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handling specific exception for Bad Request Input for Date Time Parsing
     * 
     * @param exception - Instance of DateTimeParseException class
     * @param request - WebService request
     * @return - Response Entity with Error Details and Status.
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> dateTimeParsingHandling(DateTimeParseException exception, WebRequest request) {
        ErrorDetails errorDetails = 
            new ErrorDetails(new Date(), "Invalid Data in Order Time(UTC), Kindly verify the data!", 
                                request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

	/**
     * Handling Generic Exception
     * 
     * @param exception - Instance of Exception class
     * @param request - WebService request
     * @return - Response Entity with Error Details and Status.
     */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
		ErrorDetails errorDetails = 
            new ErrorDetails(new Date(), "Internal Server Exception - "+exception.getMessage() , request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}