package com.eventsphere.ticketservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        if (requestPath.startsWith("/swagger-ui")
                || requestPath.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(
                ApiKeyConstants.API_KEY_HEADER
        );

        if (!ApiKeyConstants.API_KEY.equals(apiKey)) {

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