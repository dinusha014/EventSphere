package com.eventsphere.eventservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventsphere.eventservice.model.Event;
import com.eventsphere.eventservice.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {

        event.setId(null);

        if (event.getEventStatus() == null || event.getEventStatus().isBlank()) {
            event.setEventStatus("ACTIVE");
        }

        if (event.getAvailableTickets() == 0) {
            event.setAvailableTickets(event.getTotalTickets());
        }

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event not found with ID: " + id)
                );
    }

    public List<Event> getEventsByStatus(String status) {
        return eventRepository.findByEventStatus(status);
    }

    public List<Event> getEventsByVenue(String venue) {
        return eventRepository.findByVenue(venue);
    }

    public Event updateEvent(String id, Event updatedEvent) {

        Event existingEvent = getEventById(id);

        existingEvent.setEventName(updatedEvent.getEventName());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setEventDate(updatedEvent.getEventDate());
        existingEvent.setTicketPrice(updatedEvent.getTicketPrice());
        existingEvent.setTotalTickets(updatedEvent.getTotalTickets());
        existingEvent.setAvailableTickets(updatedEvent.getAvailableTickets());
        existingEvent.setEventStatus(updatedEvent.getEventStatus());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(String id) {

        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with ID: " + id);
        }

        eventRepository.deleteById(id);
    }

    public boolean checkAvailability(String id) {

        Event event = getEventById(id);

        return event.getAvailableTickets() > 0;
    }
}