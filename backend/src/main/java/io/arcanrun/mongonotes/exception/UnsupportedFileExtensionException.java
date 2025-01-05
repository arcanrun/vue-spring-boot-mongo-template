package io.arcanrun.mongonotes.exception;

public class UnsupportedFileExtensionException extends ApplicationRuntimeException {

  public UnsupportedFileExtensionException() {
    super(ExceptionMessages.UNSUPPORTED_FILE_EXTENSION_MESSAGE);
  }

  public UnsupportedFileExtensionException(String message) {
    super(message);
  }

  public UnsupportedFileExtensionException(String message, Throwable cause) {
    super(message, cause);
  }
}
