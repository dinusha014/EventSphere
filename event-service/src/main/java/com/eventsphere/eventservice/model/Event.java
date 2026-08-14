package com.eventsphere.eventservice.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @MongoId
    private String id;

    @NotBlank(message = "Event name is required")
    private String eventName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Venue is required")
    private String venue;

    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDate;

    @PositiveOrZero(message = "Ticket price cannot be negative")
    private double ticketPrice;

    @Min(value = 1, message = "Total tickets must be at least 1")
    private int totalTickets;

    @PositiveOrZero(message = "Available tickets cannot be negative")
    private int availableTickets;

    @NotBlank(message = "Event status is required")
    private String eventStatus;
}