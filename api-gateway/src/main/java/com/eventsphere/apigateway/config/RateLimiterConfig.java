package com.eventsphere.apigateway.config;

import java.time.Duration;

import com.github.benmanes.caffeine.cache.Caffeine;

import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.github.bucket4j.distributed.proxy.AsyncProxyManager;
import io.github.bucket4j.distributed.remote.RemoteBucketState;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public AsyncProxyManager<String> caffeineProxyManager() {

        Caffeine<String, RemoteBucketState> builder =
                (Caffeine) Caffeine.newBuilder()
                        .maximumSize(100);

        return new CaffeineProxyManager<>(
                builder,
                Duration.ofMinutes(1)
        ).asAsync();
    }
}