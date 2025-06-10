package com.anykeysolutions.service;

import com.anykeysolutions.dto.CreateBookingRequest;
import com.anykeysolutions.entity.Booking;
import com.anykeysolutions.entity.Mechanic;
import com.anykeysolutions.entity.ServiceType;
import com.anykeysolutions.entity.TimeSlot;
import com.anykeysolutions.repository.BookingRepository;
import com.anykeysolutions.repository.MechanicRepository;
import com.anykeysolutions.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MechanicRepository mechanicRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotService timeSlotService;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                         MechanicRepository mechanicRepository,
                         TimeSlotRepository timeSlotRepository,
                         TimeSlotService timeSlotService) {
        this.bookingRepository = bookingRepository;
        this.mechanicRepository = mechanicRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotService = timeSlotService;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByMechanic(Long mechanicId) {
        return mechanicRepository.findById(mechanicId)
                .map(bookingRepository::findByMechanic)
                .orElse(List.of());
    }

    public List<Booking> getBookingsByServiceType(ServiceType serviceType) {
        return bookingRepository.findByServiceType(serviceType);
    }

    public List<Booking> searchBookingsByCustomerName(String customerName) {
        return bookingRepository.findByCustomerNameContainingIgnoreCase(customerName);
    }

    public Booking createBooking(CreateBookingRequest request) {
        // Validate mechanic exists
        Mechanic mechanic = mechanicRepository.findById(request.getMechanicId())
                .orElseThrow(() -> new IllegalArgumentException("Mechanic not found with ID: " + request.getMechanicId()));

        // Validate time slot exists and is available
        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new IllegalArgumentException("Time slot not found with ID: " + request.getTimeSlotId()));

        if (!timeSlot.isAvailable()) {
            throw new IllegalStateException("Time slot is not available");
        }

        // Validate that the time slot belongs to the specified mechanic
        if (!timeSlot.getMechanic().getId().equals(request.getMechanicId())) {
            throw new IllegalArgumentException("Time slot does not belong to the specified mechanic");
        }

        // Validate that mechanic has the required skill
        if (!mechanic.hasSkill(request.getServiceType())) {
            throw new IllegalArgumentException("Mechanic does not have the required skill: " + request.getServiceType());
        }

        // Validate that the time slot is not in the past
        if (timeSlot.isInPast()) {
            throw new IllegalArgumentException("Cannot book a time slot in the past");
        }

        // Create the booking
        Booking booking = new Booking(
                request.getCustomerName(),
                request.getPhoneNumber(),
                request.getDescription(),
                request.getServiceType(),
                mechanic,
                timeSlot
        );

        // Save the booking
        Booking savedBooking = bookingRepository.save(booking);

        // Mark the time slot as unavailable
        timeSlotService.markSlotAsUnavailable(timeSlot.getId());

        return savedBooking;
    }

    public boolean deleteBooking(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            TimeSlot timeSlot = booking.getTimeSlot();

            // Delete the booking
            bookingRepository.deleteById(id);

            // Mark the time slot as available again if it's not in the past
            if (!timeSlot.isInPast()) {
                timeSlotService.markSlotAsAvailable(timeSlot.getId());
            }

            return true;
        }
        return false;
    }

    public List<Booking> getBookingsInTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return bookingRepository.findByTimeRange(startTime, endTime);
    }

    public List<Booking> getBookingsCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public boolean canMechanicProvideService(Long mechanicId, ServiceType serviceType) {
        return mechanicRepository.findById(mechanicId)
                .map(mechanic -> mechanic.hasSkill(serviceType))
                .orElse(false);
    }
}
