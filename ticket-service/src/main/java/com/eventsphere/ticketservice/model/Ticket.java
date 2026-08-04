package com.eventsphere.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    private String id;

    private String bookingId;

    private String eventId;

    private String customerName;

    private String customerEmail;

    private int ticketCount;

    private String qrCode;

    private String ticketStatus;
}