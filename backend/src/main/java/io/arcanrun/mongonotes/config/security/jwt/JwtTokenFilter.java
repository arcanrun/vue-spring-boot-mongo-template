package io.arcanrun.mongonotes.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String BEARER_HEADER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        var jwtTokenOptional = extractJwtFrom(request);

        if (jwtTokenOptional.isEmpty()) {
            filterChain.doFilter(request, response);

            return;
        }

        try {
            var authentication = jwtTokenProvider.buildAuthentication(jwtTokenOptional.get());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }


    private Optional<String> extractJwtFrom(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER_KEY))
                .filter(header -> header.startsWith(BEARER_HEADER_PREFIX))
                .map(header -> header.substring(BEARER_HEADER_PREFIX.length()));
    }
}
