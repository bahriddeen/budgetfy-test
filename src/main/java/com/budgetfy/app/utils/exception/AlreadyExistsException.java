package com.budgetfy.app.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for situations where an operation cannot be completed because the requested resource already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new AlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public AlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new AlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the getCause() method)
     */
    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
