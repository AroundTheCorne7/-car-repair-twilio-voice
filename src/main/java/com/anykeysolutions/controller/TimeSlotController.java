package com.anykeysolutions.controller;

import com.anykeysolutions.dto.GenerateTimeSlotsRequest;
import com.anykeysolutions.dto.TimeSlotResponseDto;
import com.anykeysolutions.dto.UpdateTimeSlotRequest;
import com.anykeysolutions.entity.ServiceType;
import com.anykeysolutions.entity.TimeSlot;
import com.anykeysolutions.service.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/timeslots")
@Tag(name = "Time Slots", description = "Time slot management operations")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping
    @Operation(summary = "Get all time slots", description = "Retrieve all time slots")
    public ResponseEntity<List<TimeSlotResponseDto>> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlots();
        List<TimeSlotResponseDto> timeSlotDtos = timeSlots.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(timeSlotDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get time slot by ID", description = "Retrieve a specific time slot by ID")
    public ResponseEntity<TimeSlotResponseDto> getTimeSlotById(
            @Parameter(description = "Time slot ID") @PathVariable Long id) {
        return timeSlotService.getTimeSlotById(id)
                .map(timeSlot -> ResponseEntity.ok(convertToDto(timeSlot)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    @Operation(summary = "Get available time slots", description = "Get available time slots, optionally filtered by service type")
    public ResponseEntity<List<TimeSlotResponseDto>> getAvailableTimeSlots(
            @Parameter(description = "Service type to filter by") @RequestParam(required = false) ServiceType serviceType) {
        List<TimeSlot> timeSlots;
        if (serviceType != null) {
            timeSlots = timeSlotService.getAvailableTimeSlotsByServiceType(serviceType);
        } else {
            timeSlots = timeSlotService.getAvailableTimeSlots();
        }
        List<TimeSlotResponseDto> timeSlotDtos = timeSlots.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(timeSlotDtos);
    }

    @GetMapping("/mechanic/{mechanicId}/available")
    @Operation(summary = "Get available time slots for mechanic", description = "Get available time slots for a specific mechanic")
    public ResponseEntity<List<TimeSlotResponseDto>> getAvailableTimeSlotsForMechanic(
            @Parameter(description = "Mechanic ID") @PathVariable Long mechanicId) {
        List<TimeSlot> timeSlots = timeSlotService.getAvailableTimeSlotsForMechanic(mechanicId);
        List<TimeSlotResponseDto> timeSlotDtos = timeSlots.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(timeSlotDtos);
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate time slots", description = "Generate time slots for a mechanic over a date range")
    public ResponseEntity<List<TimeSlotResponseDto>> generateTimeSlots(
            @Valid @RequestBody GenerateTimeSlotsRequest request) {
        try {
            List<TimeSlot> generatedSlots = timeSlotService.generateTimeSlots(request);
            List<TimeSlotResponseDto> timeSlotDtos = generatedSlots.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.CREATED).body(timeSlotDtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update time slot", description = "Update a time slot's start time, end time, or availability")
    public ResponseEntity<TimeSlotResponseDto> updateTimeSlot(
            @Parameter(description = "Time slot ID") @PathVariable Long id,
            @RequestBody UpdateTimeSlotRequest request) {
        try {
            return timeSlotService.updateTimeSlot(id, request)
                    .map(timeSlot -> ResponseEntity.ok(convertToDto(timeSlot)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete time slot", description = "Delete a time slot by ID")
    public ResponseEntity<Void> deleteTimeSlot(
            @Parameter(description = "Time slot ID") @PathVariable Long id) {
        try {
            if (timeSlotService.deleteTimeSlot(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private TimeSlotResponseDto convertToDto(TimeSlot timeSlot) {
        return new TimeSlotResponseDto(
                timeSlot.getId(),
                timeSlot.getStartTime(),
                timeSlot.getEndTime(),
                timeSlot.isAvailable(),
                timeSlot.getMechanic().getId(),
                timeSlot.getMechanic().getName()
        );
    }
}
