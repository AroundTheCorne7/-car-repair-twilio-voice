package com.anykeysolutions.service;

import com.anykeysolutions.dto.GenerateTimeSlotsRequest;
import com.anykeysolutions.dto.UpdateTimeSlotRequest;
import com.anykeysolutions.entity.Mechanic;
import com.anykeysolutions.entity.ServiceType;
import com.anykeysolutions.entity.TimeSlot;
import com.anykeysolutions.repository.MechanicRepository;
import com.anykeysolutions.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final MechanicRepository mechanicRepository;

    @Autowired
    public TimeSlotService(TimeSlotRepository timeSlotRepository, MechanicRepository mechanicRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.mechanicRepository = mechanicRepository;
    }

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public Optional<TimeSlot> getTimeSlotById(Long id) {
        return timeSlotRepository.findById(id);
    }

    public List<TimeSlot> getAvailableTimeSlots() {
        return timeSlotRepository.findByAvailableTrue();
    }

    public List<TimeSlot> getAvailableTimeSlotsByServiceType(ServiceType serviceType) {
        return timeSlotRepository.findAvailableSlotsByServiceType(serviceType);
    }

    public List<TimeSlot> getAvailableTimeSlotsForMechanic(Long mechanicId) {
        return mechanicRepository.findById(mechanicId)
                .map(timeSlotRepository::findByMechanicAndAvailableTrue)
                .orElse(new ArrayList<>());
    }

    public List<TimeSlot> generateTimeSlots(GenerateTimeSlotsRequest request) {
        Optional<Mechanic> mechanicOpt = mechanicRepository.findById(request.getMechanicId());
        if (mechanicOpt.isEmpty()) {
            throw new IllegalArgumentException("Mechanic not found with ID: " + request.getMechanicId());
        }

        Mechanic mechanic = mechanicOpt.get();
        List<TimeSlot> generatedSlots = new ArrayList<>();

        LocalDate currentDate = request.getStartDate();
        while (!currentDate.isAfter(request.getEndDate())) {
            LocalDateTime slotStart = LocalDateTime.of(currentDate, request.getWorkingStartTime());
            LocalDateTime dayEnd = LocalDateTime.of(currentDate, request.getWorkingEndTime());

            while (slotStart.plusMinutes(request.getSlotDurationMinutes()).isBefore(dayEnd) ||
                   slotStart.plusMinutes(request.getSlotDurationMinutes()).equals(dayEnd)) {
                
                LocalDateTime slotEnd = slotStart.plusMinutes(request.getSlotDurationMinutes());

                // Check for overlapping slots
                List<TimeSlot> overlapping = timeSlotRepository.findOverlappingSlots(mechanic, slotStart, slotEnd);
                if (overlapping.isEmpty()) {
                    TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd, mechanic);
                    generatedSlots.add(timeSlot);
                }

                slotStart = slotEnd;
            }

            currentDate = currentDate.plusDays(1);
        }

        return timeSlotRepository.saveAll(generatedSlots);
    }

    public Optional<TimeSlot> updateTimeSlot(Long id, UpdateTimeSlotRequest request) {
        return timeSlotRepository.findById(id)
                .map(timeSlot -> {
                    if (request.getStartTime() != null) {
                        timeSlot.setStartTime(request.getStartTime());
                    }
                    if (request.getEndTime() != null) {
                        timeSlot.setEndTime(request.getEndTime());
                    }
                    if (request.getAvailable() != null) {
                        timeSlot.setAvailable(request.getAvailable());
                    }

                    // Validate that start time is before end time
                    if (timeSlot.getStartTime().isAfter(timeSlot.getEndTime()) ||
                        timeSlot.getStartTime().equals(timeSlot.getEndTime())) {
                        throw new IllegalArgumentException("Start time must be before end time");
                    }

                    // Check for overlapping slots if times are being updated
                    if (request.getStartTime() != null || request.getEndTime() != null) {
                        List<TimeSlot> overlapping = timeSlotRepository.findOverlappingSlots(
                                timeSlot.getMechanic(), timeSlot.getStartTime(), timeSlot.getEndTime());
                        overlapping.removeIf(slot -> slot.getId().equals(timeSlot.getId()));
                        
                        if (!overlapping.isEmpty()) {
                            throw new IllegalArgumentException("Time slot overlaps with existing slots");
                        }
                    }

                    return timeSlotRepository.save(timeSlot);
                });
    }

    public boolean deleteTimeSlot(Long id) {
        Optional<TimeSlot> timeSlotOpt = timeSlotRepository.findById(id);
        if (timeSlotOpt.isPresent()) {
            TimeSlot timeSlot = timeSlotOpt.get();
            if (timeSlot.getBooking() != null) {
                throw new IllegalStateException("Cannot delete time slot that has a booking");
            }
            timeSlotRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void markSlotAsUnavailable(Long timeSlotId) {
        timeSlotRepository.findById(timeSlotId)
                .ifPresent(timeSlot -> {
                    timeSlot.setAvailable(false);
                    timeSlotRepository.save(timeSlot);
                });
    }

    public void markSlotAsAvailable(Long timeSlotId) {
        timeSlotRepository.findById(timeSlotId)
                .ifPresent(timeSlot -> {
                    if (timeSlot.getBooking() == null) {
                        timeSlot.setAvailable(true);
                        timeSlotRepository.save(timeSlot);
                    }
                });
    }

    public boolean isSlotAvailable(Long timeSlotId) {
        return timeSlotRepository.findById(timeSlotId)
                .map(TimeSlot::isAvailable)
                .orElse(false);
    }
}
