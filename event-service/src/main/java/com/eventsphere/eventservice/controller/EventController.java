package com.eventsphere.eventservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventsphere.eventservice.model.Event;
import com.eventsphere.eventservice.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody Event event) {

        Event createdEvent = eventService.createEvent(event);

        return new ResponseEntity<>(
                createdEvent,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(
                eventService.getAllEvents()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                eventService.getEventById(id)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Event>> getEventsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                eventService.getEventsByStatus(status)
        );
    }

    @GetMapping("/venue/{venue}")
    public ResponseEntity<List<Event>> getEventsByVenue(
            @PathVariable String venue) {

        return ResponseEntity.ok(
                eventService.getEventsByVenue(venue)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable String id,
            @Valid @RequestBody Event event) {

        return ResponseEntity.ok(
                eventService.updateEvent(id, event)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(
            @PathVariable String id) {

        eventService.deleteEvent(id);

        return ResponseEntity.ok(
                "Event deleted successfully"
        );
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable String id) {

        return ResponseEntity.ok(
                eventService.checkAvailability(id)
        );
    }
}