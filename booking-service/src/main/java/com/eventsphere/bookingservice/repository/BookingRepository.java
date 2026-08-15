package com.eventsphere.bookingservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventsphere.bookingservice.model.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByCustomerEmail(String customerEmail);

    List<Booking> findByEventId(String eventId);

    List<Booking> findByBookingStatus(String bookingStatus);
}