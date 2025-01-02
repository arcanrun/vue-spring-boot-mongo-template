package io.arcanrun.mongonotes.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestConstant {

    public static final List<String> PERMIT_METHODS =
            List.of(
                    HttpMethod.OPTIONS.name(),
                    HttpMethod.GET.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.PATCH.name(),
                    HttpMethod.DELETE.name());

    public static final List<String> CORS_ALL = List.of(CorsConfiguration.ALL);

    public static final String VERSION = "v1";
    public static final String API = "api";
    public static final String API_PATH = "/" + API + "/" + VERSION;

    public static final String OK = "200";
    public static final String CREATED = "201";
    public static final String UNAUTHORIZED = "401";
    public static final String FORBIDDEN = "403";
    public static final String TOO_MANY_REQUESTS = "409";
    public static final String NOT_FOUND = "404";
    public static final String INTERNAL_SERVER_ERROR = "500";
}
