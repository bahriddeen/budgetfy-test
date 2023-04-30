package com.budgetfy.app.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is used to represent situations where a requested resource is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with no message or cause.
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the getCause() method)
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
