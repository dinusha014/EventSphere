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

                        // =========================
                        // Event Service
                        // =========================

                        // Read events
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/events/**"
                        )
                        .hasAuthority("SCOPE_events.read")

                        // Create events
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/events/**"
                        )
                        .hasAuthority("SCOPE_events.write")

                        // Update events
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/events/**"
                        )
                        .hasAuthority("SCOPE_events.write")

                        // Delete events
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/events/**"
                        )
                        .hasAuthority("SCOPE_events.write")


                        // =========================
                        // Booking Service
                        // =========================

                        // Read bookings
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bookings/**"
                        )
                        .hasAuthority("SCOPE_bookings.read")

                        // Create bookings
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/bookings/**"
                        )
                        .hasAuthority("SCOPE_bookings.write")

                        // Cancel / update bookings
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/bookings/**"
                        )
                        .hasAuthority("SCOPE_bookings.write")


                        // =========================
                        // Ticket Service
                        // =========================

                        // Read tickets
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tickets/**"
                        )
                        .hasAuthority("SCOPE_tickets.read")

                        // Generate tickets
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/tickets/**"
                        )
                        .hasAuthority("SCOPE_tickets.write")

                        // Cancel / update tickets
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/tickets/**"
                        )
                        .hasAuthority("SCOPE_tickets.write")

                        // Delete tickets
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/tickets/**"
                        )
                        .hasAuthority("SCOPE_tickets.write")


                        // Any other API route requires authentication
                        .anyRequest()
                        .authenticated()
                )

                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}