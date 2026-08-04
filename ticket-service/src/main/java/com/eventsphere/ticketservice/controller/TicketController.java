package com.eventsphere.ticketservice.controller;

import com.eventsphere.ticketservice.model.Ticket;
import com.eventsphere.ticketservice.service.TicketService;
import jakarta.validation.Valid;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
   public ResponseEntity<Ticket> generateTicket(
        @Valid @RequestBody Ticket ticket
)
     {
        Ticket createdTicket = ticketService.generateTicket(ticket);

        return new ResponseEntity<>(
                createdTicket,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(
                ticketService.getAllTickets()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(
                ticketService.getTicketById(id)
        );
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Ticket> getTicketByBookingId(
            @PathVariable String bookingId
    ) {
        return ResponseEntity.ok(
                ticketService.getTicketByBookingId(bookingId)
        );
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Ticket>> getTicketsByCustomerEmail(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(
                ticketService.getTicketsByCustomerEmail(email)
        );
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Ticket> cancelTicket(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(
                ticketService.cancelTicket(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable String id
    ) {
        ticketService.deleteTicket(id);

        return ResponseEntity.ok(
                "Ticket deleted successfully"
        );
    }
}