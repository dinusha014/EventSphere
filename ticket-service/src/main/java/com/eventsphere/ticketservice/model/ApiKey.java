package com.eventsphere.ticketservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "api_keys")
public class ApiKey {

    @Id
    private String id;

    private String apiKey;

    private String serviceName;

    private boolean active;

    public ApiKey() {
    }

    public ApiKey(String apiKey, String serviceName, boolean active) {
        this.apiKey = apiKey;
        this.serviceName = serviceName;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}