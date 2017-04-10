package com.rest.jobprocessor.exceptions;

/**
 * Exception to be thrown for byte array conversion related errors.
 */
public class ByteArrayConversionException extends Exception {

    /**
     * Default constructor to instantiate ByteArrayConversionException.
     */
    public ByteArrayConversionException() {
        super();
    }

    /**
     * Constructor to instantiate ByteArrayConversionException with the message.
     *
     * @param message Exception message
     */
    public ByteArrayConversionException(String message) {
        super(message);
    }

    /**
     * Constructor to instantiate ByteArrayConversionException with the message and the cause.
     *
     * @param message Exception message
     * @param cause   Cause for the exception
     */
    public ByteArrayConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
