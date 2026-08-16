package com.eventsphere.bookingservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eventsphere.bookingservice.model.Booking;
import com.eventsphere.bookingservice.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Create a new booking
    public Booking createBooking(Booking booking) {

        booking.setId(null);

        double totalAmount =
                booking.getTicketCount() * booking.getTicketPrice();

        booking.setTotalAmount(totalAmount);
        booking.setBookingStatus("CONFIRMED");
        booking.setBookingDate(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    // View all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // View a booking by ID
    public Booking getBookingById(String id) {

        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Booking not found with ID: " + id
                        )
                );
    }

    // View bookings belonging to a user
    public List<Booking> getBookingsByCustomerEmail(
            String customerEmail) {

        return bookingRepository.findByCustomerEmail(customerEmail);
    }

    // Cancel a booking
    public Booking cancelBooking(String id) {

        Booking booking = getBookingById(id);

        booking.setBookingStatus("CANCELLED");

        return bookingRepository.save(booking);
    }
}