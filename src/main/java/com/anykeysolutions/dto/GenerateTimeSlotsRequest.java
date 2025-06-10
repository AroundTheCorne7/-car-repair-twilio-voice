package com.anykeysolutions.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public class GenerateTimeSlotsRequest {

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Working start time is required")
    private LocalTime workingStartTime;

    @NotNull(message = "Working end time is required")
    private LocalTime workingEndTime;

    @NotNull(message = "Slot duration in minutes is required")
    @Positive(message = "Slot duration must be positive")
    private Integer slotDurationMinutes;

    @NotNull(message = "Mechanic ID is required")
    private Long mechanicId;

    // Constructors
    public GenerateTimeSlotsRequest() {}

    public GenerateTimeSlotsRequest(LocalDate startDate, LocalDate endDate, 
                                   LocalTime workingStartTime, LocalTime workingEndTime,
                                   Integer slotDurationMinutes, Long mechanicId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.workingStartTime = workingStartTime;
        this.workingEndTime = workingEndTime;
        this.slotDurationMinutes = slotDurationMinutes;
        this.mechanicId = mechanicId;
    }

    // Getters and Setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getWorkingStartTime() {
        return workingStartTime;
    }

    public void setWorkingStartTime(LocalTime workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public LocalTime getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(LocalTime workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    public Integer getSlotDurationMinutes() {
        return slotDurationMinutes;
    }

    public void setSlotDurationMinutes(Integer slotDurationMinutes) {
        this.slotDurationMinutes = slotDurationMinutes;
    }

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }
}
