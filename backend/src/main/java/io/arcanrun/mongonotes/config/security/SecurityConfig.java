package io.arcanrun.mongonotes.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.arcanrun.mongonotes.config.security.jwt.JwtTokenConfigurer;
import io.arcanrun.mongonotes.config.security.jwt.JwtTokenProvider;
import io.arcanrun.mongonotes.exception.ApiErrorDto;
import io.arcanrun.mongonotes.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static io.arcanrun.mongonotes.config.security.SecurityConstant.UNPROTECTED_ENDPOINTS;
import static io.arcanrun.mongonotes.util.RestConstant.API_PATH;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectMapper objectMapper,
                                                   JwtTokenProvider jwtTokenProvider,
                                                   HandlerExceptionResolver handlerExceptionResolver) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(
                        exceptionHandlingConfigurer ->
                                exceptionHandlingConfigurer
                                        .accessDeniedHandler(customAccessDeniedHandler(objectMapper))
                                        .authenticationEntryPoint(customAuthenticationEntryPoint(objectMapper))
                )
                .headers(
                        headersConfigurer ->
                                headersConfigurer.frameOptions(Customizer.withDefaults()).disable())
                .sessionManagement(
                        sessionManagementConfigurer ->
                                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        requests ->
                                // @formatter:off
                                requests
                                        .requestMatchers(API_PATH + "/auth/refresh/**")
                                            .hasAuthority(Authority.REFRESH_TOKEN.name())
                                        .requestMatchers(UNPROTECTED_ENDPOINTS)
                                            .permitAll()
                                        .anyRequest()
                                            .authenticated()
                                )
                                // @formatter:on
                .with(new JwtTokenConfigurer(jwtTokenProvider, handlerExceptionResolver), Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint(ObjectMapper objectMapper) {
        return (request, response, authException) -> {
            var apiError =
                    new ApiErrorDto(HttpStatus.UNAUTHORIZED, LocalDateTime.now(), authException.getMessage());
            var apiErrorRepresentation = objectMapper.writeValueAsString(apiError);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().write(apiErrorRepresentation);

            log.error(apiErrorRepresentation);
        };
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler(ObjectMapper objectMapper) {
        return (request, response, accessDeniedException) -> {
            var apiError =
                    new ApiErrorDto(
                            HttpStatus.FORBIDDEN, LocalDateTime.now(), accessDeniedException.getMessage());
            var apiErrorRepresentation = objectMapper.writeValueAsString(apiError);

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().write(apiErrorRepresentation);

            log.error(apiErrorRepresentation);
        };
    }
}
