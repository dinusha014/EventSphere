package com.eventsphere.eventservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventsphere.eventservice.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByEventStatus(String eventStatus);

    List<Event> findByVenue(String venue);
}