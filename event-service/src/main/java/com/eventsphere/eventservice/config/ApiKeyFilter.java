package com.eventsphere.eventservice.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventsphere.eventservice.repository.ApiKeyRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String SERVICE_NAME = "event-service";

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyFilter(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        if (requestPath.startsWith("/api/events")) {

            String providedApiKey = request.getHeader(API_KEY_HEADER);

            boolean validApiKey =
                    providedApiKey != null &&
                    apiKeyRepository
                            .findByApiKeyAndServiceNameAndActive(
                                    providedApiKey,
                                    SERVICE_NAME,
                                    true
                            )
                            .isPresent();

            if (!validApiKey) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                response.getWriter().write(
                        "{\"status\":401,"
                                + "\"error\":\"Unauthorized\","
                                + "\"message\":\"Invalid or missing API key\"}"
                );

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}