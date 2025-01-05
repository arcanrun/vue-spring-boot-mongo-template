package io.arcanrun.mongonotes.exception;

import org.apache.commons.lang3.StringUtils;

public class ResourceNotFoundException extends ApplicationRuntimeException {

  public ResourceNotFoundException() {
    super(ExceptionMessages.RESOURCE_NOT_FOUND_DEFAULT_MESSAGE);
  }

  public ResourceNotFoundException(Class clazz) {
    super(ResourceNotFoundException.generateMessage(clazz.getSimpleName()));
  }

  public ResourceNotFoundException(final String message) {
    super(message);
  }

  public ResourceNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  private static String generateMessage(String entity) {
    return String.format(
        "%s %s",
        ExceptionMessages.RESOURCE_NOT_FOUND_MESSAGE_PREFIX, StringUtils.capitalize(entity));
  }
}
