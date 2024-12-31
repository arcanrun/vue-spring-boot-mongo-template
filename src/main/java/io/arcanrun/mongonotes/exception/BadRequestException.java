package io.arcanrun.mongonotes.exception;


public class BadRequestException extends ApplicationRuntimeException {

    public BadRequestException() {
        super(ExceptionMessages.BAD_REQUEST_DEFAULT_MESSAGE);
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
