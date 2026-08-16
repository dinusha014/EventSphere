package com.eventsphere.bookingservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventsphere.bookingservice.model.ApiKey;

public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {

    Optional<ApiKey> findByApiKeyAndServiceNameAndActive(
            String apiKey,
            String serviceName,
            boolean active
    );
}