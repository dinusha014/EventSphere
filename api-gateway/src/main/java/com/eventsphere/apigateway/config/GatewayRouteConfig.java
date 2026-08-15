package com.eventsphere.apigateway.config;

import java.time.Duration;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.Bucket4jFilterFunctions.rateLimit;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouterFunction<ServerResponse> eventServiceRoute() {

        return route("event-service")
                .route(request ->
                        request.path().startsWith("/api/events"),
                        http()
                )
                .before(uri("http://localhost:8081"))
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
                .route(request ->
                        request.path().startsWith("/api/tickets"),
                        http()
                )
                .before(uri("http://localhost:8082"))
                .build();
    }
}