package com.eventsphere.ticketservice.service;

import com.eventsphere.ticketservice.model.Ticket;
import com.eventsphere.ticketservice.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket generateTicket(Ticket ticket) {

        ticket.setId(null);

        String qrCodeValue = "EVENTSPHERE-" + UUID.randomUUID();
        ticket.setQrCode(qrCodeValue);

        if (ticket.getTicketStatus() == null ||
                ticket.getTicketStatus().isBlank()) {
            ticket.setTicketStatus("ACTIVE");
        }

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found with ID: " + id));
    }

    public Ticket getTicketByBookingId(String bookingId) {
        return ticketRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found for booking ID: " + bookingId
                        ));
    }

    public List<Ticket> getTicketsByCustomerEmail(String customerEmail) {
        return ticketRepository.findByCustomerEmail(customerEmail);
    }

    public Ticket cancelTicket(String id) {

        Ticket ticket = getTicketById(id);

        ticket.setTicketStatus("CANCELLED");

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(String id) {

        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException(
                    "Ticket not found with ID: " + id
            );
        }

        ticketRepository.deleteById(id);
    }
}