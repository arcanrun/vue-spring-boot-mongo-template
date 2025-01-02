package io.arcanrun.mongonotes.exception;

import lombok.Getter;

@Getter
public class ValidationException extends ApplicationRuntimeException {

    private String fieldName;

    private String errorMessage;

    public ValidationException(String fieldName, String errorMessage) {
        super(buildExceptionMessage(fieldName, errorMessage));
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public ValidationException() {
        super(ExceptionMessages.VALIDATION_ERROR_DEFAULT_MESSAGE);
    }

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    private static String buildExceptionMessage(String fieldName, String errorMessage) {
        return String.format(ExceptionMessages.VALIDATION_ERROR_FOR_FIELD, fieldName, errorMessage);
    }
}
