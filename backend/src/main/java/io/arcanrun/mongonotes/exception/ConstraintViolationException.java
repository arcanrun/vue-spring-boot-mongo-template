package io.arcanrun.mongonotes.exception;

public class ConstraintViolationException extends ApplicationRuntimeException {

    public ConstraintViolationException() {
        super(ExceptionMessages.CONSTRAINT_VIOLATION_ERROR);
    }

    public ConstraintViolationException(final String message) {
        super(message);
    }

    public ConstraintViolationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
