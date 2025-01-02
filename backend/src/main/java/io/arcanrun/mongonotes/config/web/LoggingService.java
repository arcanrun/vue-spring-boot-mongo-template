package io.arcanrun.mongonotes.config.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class LoggingService {

    public void displayRequest(HttpServletRequest request, Object body) {
        var reqMessage = new StringBuilder();
        var parameters = getParameters(request);

        reqMessage.append("[").append(request.getMethod()).append("]");
        reqMessage.append(" path = [").append(request.getRequestURI()).append("] ");

        if (!parameters.isEmpty()) {
            reqMessage.append(" parameters = [").append(parameters).append("] ");
        }

        if (!Objects.isNull(body)) {
            reqMessage.append(" body = [").append(body).append("]");
        }

        log.info("Request: {}", reqMessage);
    }

    public void displayResponse(
            HttpServletRequest request, HttpServletResponse response, Object body) {
        var respMessage = new StringBuilder();
        var headers = getHeaders(response);

        respMessage.append("[").append(request.getMethod()).append("]");

        if (!headers.isEmpty()) {
            respMessage.append(" ResponseHeaders = [").append(headers).append("]");
        }

        respMessage.append(" responseBody = [").append(body).append("]");

        log.info("Response: {}", respMessage);
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        var headerMap = response.getHeaderNames();
        for (String str : headerMap) {
            headers.put(str, response.getHeader(str));
        }

        return headers;
    }

    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        var params = request.getParameterNames();
        while (params.hasMoreElements()) {
            var paramName = params.nextElement();
            var paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }

        return parameters;
    }
}
