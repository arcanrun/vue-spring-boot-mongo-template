package io.arcanrun.mongonotes.config.logging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingConstant {
  public static final String REQUEST_ID_KEY = "request_id";
  public static final String REQUEST_RESOURCE_URI = "request_resource_uri";
  public static final String REQUEST_RESOURCE_METHOD = "request_resource_method";
}
