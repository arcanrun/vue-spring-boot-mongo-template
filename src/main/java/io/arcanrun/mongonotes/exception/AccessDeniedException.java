package io.arcanrun.mongonotes.exception;


public class AccessDeniedException extends ApplicationRuntimeException {

    public AccessDeniedException() {
        super(ExceptionMessages.ACCESS_DENIED_MESSAGE);
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
