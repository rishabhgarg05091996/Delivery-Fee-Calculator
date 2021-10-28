/**
 * This is a Resource Not Found Exception class to handle Bad Request Data.
 * 
 * @author Rishabh Garg
 */
package dev.rishabh.deliveryfee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException{

    /**
     * Constructor to consume Exception message.
     * 
     * @param message - Exception message.
     */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}