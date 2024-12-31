package io.arcanrun.mongonotes.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class to centralize and organize exception messages used in the application. This class
 * encapsulates constant strings representing various error messages, making it easier to manage and
 * customize error responses across the application.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessages {

    // Generic Exception
    public static final String GENERIC_EXCEPTION_DEFAULT_MESSAGE = "Internal server error";

    // Validation Exceptions
    public static final String VALIDATION_ERROR_FOR_FIELD = "Validation error for field '%s': %s";
    public static final String VALIDATION_ERROR_DEFAULT_MESSAGE = "Request validation failed";

    // Authentication Exceptions
    public static final String AUTHENTICATION_ERROR_MESSAGE = "Authentication Error: ";
    public static final String BAD_CREDENTIALS_MESSAGE = "Invalid username or password";
    public static final String ACCOUNT_LOCKED_MESSAGE = "Account is locked";

    // Access Denied Exception
    public static final String ACCESS_DENIED_MESSAGE = "Access Denied: ";

    // Authorization Exceptions
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized: ";

    // Resource Not Found Exception
    public static final String RESOURCE_NOT_FOUND_MESSAGE_PREFIX = "Resource not found:";
    public static final String RESOURCE_NOT_FOUND_DEFAULT_MESSAGE =
            "The requested resource could not be found";

    // Bad Request Exception
    public static final String BAD_REQUEST_DEFAULT_MESSAGE = "Bad Request";

    // Constraint Violation
    public static final String CONSTRAINT_VIOLATION_ERROR =
            "Constraint violation detected during validation";

    // Unsupported File Extension
    public static final String UNSUPPORTED_FILE_EXTENSION_MESSAGE = "File extension not supported ";
}
