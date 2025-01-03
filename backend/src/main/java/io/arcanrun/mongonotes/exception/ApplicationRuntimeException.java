package io.arcanrun.mongonotes.exception;

/**
 * Custom runtime exception specific to the application. Extends {@link RuntimeException}.
 */
public class ApplicationRuntimeException extends RuntimeException {

  public ApplicationRuntimeException() {}

  public ApplicationRuntimeException(final String message) {
    super(message);
  }

  public ApplicationRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ApplicationRuntimeException(final Throwable cause) {
    super(cause);
  }
}
