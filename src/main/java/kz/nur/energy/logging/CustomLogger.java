package kz.nur.energy.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;

@Component
public class CustomLogger implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CustomLogger.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("Incoming Request: Method={}, URI={}, QueryParams={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString() != null ? request.getQueryString() : "");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        int status = response.getStatus();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (ex != null) {
            logger.error("Outgoing Response with Exception: Method={}, URI={}, Status={}, Exception={}",
                    method, uri, status, ex.getMessage(), ex);
        } else if (status >= 400 && status < 500) {
            logger.warn("Client Error Response: Method={}, URI={}, Status={}", method, uri, status);
        } else if (status >= 500) {
            logger.error("Server Error Response: Method={}, URI={}, Status={}", method, uri, status);
        } else {
            logger.info("Outgoing Response: Method={}, URI={}, Status={}", method, uri, status);
        }
    }
}
