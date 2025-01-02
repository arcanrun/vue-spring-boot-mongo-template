package io.arcanrun.mongonotes.config.web;

import io.swagger.v3.oas.models.PathItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

  private final LoggingService loggingService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
          throws Exception {
    // Log details of HTTP requests for GET, DELETE, and PUT methods
    if (request.getMethod().equals(PathItem.HttpMethod.GET.name())
            || request.getMethod().equals(HttpMethod.DELETE.name())
            || request.getMethod().equals(HttpMethod.PUT.name())) {
      loggingService.displayRequest(request, null);
    }
    return true;
  }
}
