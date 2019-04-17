package com.arayan.stackedcups.model.exceptions;

/**
 * Simple Exception thrown when the arguments provided to methods
 * are not correct/invalid
 */
public final class InvalidArgumentException extends Exception {

    private final String fieldName;

    public InvalidArgumentException(final String fieldName, final String message) {
        super(message);

        this.fieldName = fieldName;
    }

    @Override
    public String getMessage() {
        return "Field: " + fieldName + " Message: " + super.getMessage();
    }
}
