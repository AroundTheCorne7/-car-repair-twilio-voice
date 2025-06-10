package com.anykeysolutions.dto;

import java.time.LocalDateTime;

public class UpdateTimeSlotRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean available;

    // Constructors
    public UpdateTimeSlotRequest() {}

    public UpdateTimeSlotRequest(LocalDateTime startTime, LocalDateTime endTime, Boolean available) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    // Getters and Setters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
