package com.eventsphere.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        http
                .securityMatcher(
                        authorizationServerConfigurer.getEndpointsMatcher()
                )
                .cors(Customizer.withDefaults())
                .with(
                        authorizationServerConfigurer,
                        Customizer.withDefaults()
                );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain applicationSecurityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/**")

                .cors(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Allow browser CORS preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        // Event Service read access
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/events/**"
                        )
                        .hasAuthority("SCOPE_events.read")

                        // Event Service write access
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/events/**"
                        )
                        .hasAuthority("SCOPE_events.write")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/events/**"
                        )
                        .hasAuthority("SCOPE_events.write")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/events/**"
                        )
                        .hasAuthority("SCOPE_events.write")

                        // Other API routes require authentication
                        .anyRequest()
                        .authenticated()
                )

                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}