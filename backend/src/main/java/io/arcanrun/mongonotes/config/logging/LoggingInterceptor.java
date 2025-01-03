package io.arcanrun.mongonotes.config.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.UUID;

import static io.arcanrun.mongonotes.config.logging.LoggingConstant.REQUEST_ID_KEY;
import static io.arcanrun.mongonotes.config.logging.LoggingConstant.REQUEST_RESOURCE_METHOD;
import static io.arcanrun.mongonotes.config.logging.LoggingConstant.REQUEST_RESOURCE_URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
          throws Exception {
    var requestId = UUID.randomUUID().toString();

    MDC.put(REQUEST_ID_KEY, requestId);
    MDC.put(REQUEST_RESOURCE_METHOD, request.getMethod());
    MDC.put(REQUEST_RESOURCE_URI, request.getRequestURI());
    Optional.ofNullable(response.getHeader(REQUEST_ID_KEY))
            .ifPresentOrElse(
                    s -> log.info("Request ID: {}", s),
                    () -> response.setHeader(REQUEST_ID_KEY, requestId));
    return true;
  }

  @Override
  public void afterCompletion(
          HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
          throws Exception {
    MDC.remove(REQUEST_ID_KEY);
    MDC.remove(REQUEST_RESOURCE_METHOD);
    MDC.remove(REQUEST_RESOURCE_URI);
  }
}
