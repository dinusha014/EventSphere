package com.eventsphere.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    private String id;

   @NotBlank(message = "Booking ID is required")
private String bookingId;
    @NotBlank(message = "Event ID is required")
private String eventId;

   @NotBlank(message = "Customer name is required")
private String customerName;

    @NotBlank(message = "Customer email is required")
@Email(message = "Invalid email format")
private String customerEmail;

   @Min(value = 1, message = "Ticket count must be at least 1")
private int ticketCount;

    private String qrCode;

    private String ticketStatus;
}