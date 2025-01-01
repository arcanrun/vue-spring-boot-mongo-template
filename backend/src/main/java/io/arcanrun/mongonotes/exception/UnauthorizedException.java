package io.arcanrun.mongonotes.exception;


public class UnauthorizedException extends ApplicationRuntimeException {

    public UnauthorizedException() {
        super(ExceptionMessages.UNAUTHORIZED_MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
