package com.eventsphere.apigateway.config;

import java.time.Duration;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.Bucket4jFilterFunctions.rateLimit;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayRouteConfig {

    @Value("${services.event.url:http://localhost:8081}")
    private String eventServiceUrl;

    @Value("${services.ticket.url:http://localhost:8082}")
    private String ticketServiceUrl;

    @Value("${services.booking.url:http://localhost:8083}")
    private String bookingServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> eventServiceRoute() {

        return route("event-service")
                .route(
                        request -> request.path().startsWith("/api/events"),
                        http()
                )
                .before(uri(eventServiceUrl))
                .filter(rateLimit(config -> config
                        .setCapacity(5)
                        .setPeriod(Duration.ofMinutes(1))
                        .setKeyResolver(request -> {
                            String clientIp =
                                    request.servletRequest()
                                            .getRemoteAddr();

                            return clientIp != null
                                    ? clientIp
                                    : "unknown-client";
                        })
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ticketServiceRoute() {

        return route("ticket-service")
                .route(
                        request -> request.path().startsWith("/api/tickets"),
                        http()
                )
                .before(uri(ticketServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute() {

        return route("booking-service")
                .route(
                        request -> request.path().startsWith("/api/bookings"),
                        http()
                )
                .before(uri(bookingServiceUrl))
                .build();
    }
}