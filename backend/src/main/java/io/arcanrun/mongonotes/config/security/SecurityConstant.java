package io.arcanrun.mongonotes.config.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityConstant {
  public static final RequestMatcher UNPROTECTED_ENDPOINTS =
      new OrRequestMatcher(
          new AntPathRequestMatcher("/v2/api-docs/**"),
          new AntPathRequestMatcher("/v3/api-docs/**"),
          new AntPathRequestMatcher("/swagger-ui/**"),
          new AntPathRequestMatcher("/swagger-resources/**"),
          new AntPathRequestMatcher("/actuator/**"),
          new AntPathRequestMatcher("/api/v1/hello/info"),
          new AntPathRequestMatcher("/api/v1/auth/login/**"),
          new AntPathRequestMatcher("/api/v1/auth/signup/**"),
          new AntPathRequestMatcher("/hello") // TODO: remove
          );
}
