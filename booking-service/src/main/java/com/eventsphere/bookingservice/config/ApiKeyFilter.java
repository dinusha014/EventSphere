package com.eventsphere.bookingservice.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventsphere.bookingservice.repository.ApiKeyRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String SERVICE_NAME = "booking-service";

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyFilter(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // Allow Swagger/OpenAPI without API key
        if (requestPath.startsWith("/swagger-ui")
                || requestPath.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(API_KEY_HEADER);

        boolean validApiKey =
                apiKey != null &&
                apiKeyRepository
                        .findByApiKeyAndServiceNameAndActive(
                                apiKey,
                                SERVICE_NAME,
                                true
                        )
                        .isPresent();

        if (!validApiKey) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.setContentType("application/json");

            response.getWriter().write(
                    """
                    {
                      "status": 401,
                      "error": "Unauthorized",
                      "message": "Invalid or missing API key"
                    }
                    """
            );

            return;
        }

        filterChain.doFilter(request, response);
    }
}