package com.rest.jobprocessor.exceptions;

/**
 * Exception to be thrown for Job processing related errors.
 *
 */
public class JobProcessingException extends Exception {

    /**
     * Default constructor to instantiate JobProcessingException.
     */
    public JobProcessingException() {
        super();
    }

    /**
     * Constructor to instantiate JobProcessingException with the message.
     *
     * @param message Exception message
     */
    public JobProcessingException(String message) {
        super(message);
    }

    /**
     * Constructor to instantiate JobProcessingException with the message and the cause.
     *
     * @param message Exception message
     * @param cause   Cause for the exception
     */
    public JobProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
