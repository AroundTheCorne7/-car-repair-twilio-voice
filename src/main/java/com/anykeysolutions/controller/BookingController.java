package com.anykeysolutions.controller;

import com.anykeysolutions.dto.CreateBookingRequest;
import com.anykeysolutions.entity.Booking;
import com.anykeysolutions.entity.ServiceType;
import com.anykeysolutions.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "Booking management operations")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieve all bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieve a specific booking by ID")
    public ResponseEntity<Booking> getBookingById(
            @Parameter(description = "Booking ID") @PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(booking -> ResponseEntity.ok(booking))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create booking", description = "Create a new booking")
    public ResponseEntity<Booking> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {
        try {
            Booking createdBooking = bookingService.createBooking(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel/delete booking", description = "Cancel or delete a booking by ID")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "Booking ID") @PathVariable Long id) {
        if (bookingService.deleteBooking(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/mechanic/{mechanicId}")
    @Operation(summary = "Get bookings by mechanic", description = "Retrieve all bookings for a specific mechanic")
    public ResponseEntity<List<Booking>> getBookingsByMechanic(
            @Parameter(description = "Mechanic ID") @PathVariable Long mechanicId) {
        List<Booking> bookings = bookingService.getBookingsByMechanic(mechanicId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/service-type/{serviceType}")
    @Operation(summary = "Get bookings by service type", description = "Retrieve all bookings for a specific service type")
    public ResponseEntity<List<Booking>> getBookingsByServiceType(
            @Parameter(description = "Service type") @PathVariable ServiceType serviceType) {
        List<Booking> bookings = bookingService.getBookingsByServiceType(serviceType);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/search")
    @Operation(summary = "Search bookings by customer name", description = "Search for bookings by customer name")
    public ResponseEntity<List<Booking>> searchBookingsByCustomerName(
            @Parameter(description = "Customer name to search for") @RequestParam String customerName) {
        List<Booking> bookings = bookingService.searchBookingsByCustomerName(customerName);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/time-range")
    @Operation(summary = "Get bookings in time range", description = "Retrieve bookings within a specific time range")
    public ResponseEntity<List<Booking>> getBookingsInTimeRange(
            @Parameter(description = "Start time (ISO format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "End time (ISO format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<Booking> bookings = bookingService.getBookingsInTimeRange(startTime, endTime);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/created-between")
    @Operation(summary = "Get bookings created between dates", description = "Retrieve bookings created within a specific date range")
    public ResponseEntity<List<Booking>> getBookingsCreatedBetween(
            @Parameter(description = "Start date (ISO format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (ISO format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Booking> bookings = bookingService.getBookingsCreatedBetween(startDate, endDate);
        return ResponseEntity.ok(bookings);
    }
}
