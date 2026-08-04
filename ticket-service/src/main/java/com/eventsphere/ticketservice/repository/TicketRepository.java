package com.eventsphere.ticketservice.repository;

import com.eventsphere.ticketservice.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    Optional<Ticket> findByBookingId(String bookingId);

    List<Ticket> findByCustomerEmail(String customerEmail);

    List<Ticket> findByTicketStatus(String ticketStatus);
}