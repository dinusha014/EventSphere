package com.eventsphere.bookingservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    private String id;

    @NotBlank(message = "Event ID is required")
    private String eventId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @Min(value = 1, message = "Ticket count must be at least 1")
    private int ticketCount;

    @PositiveOrZero(message = "Ticket price cannot be negative")
    private double ticketPrice;

    @PositiveOrZero(message = "Total amount cannot be negative")
    private double totalAmount;

    private String bookingStatus;

    private LocalDateTime bookingDate;
}