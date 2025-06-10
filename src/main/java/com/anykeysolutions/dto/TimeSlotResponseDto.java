package com.anykeysolutions.dto;

import java.time.LocalDateTime;

public class TimeSlotResponseDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean available;
    private Long mechanicId;
    private String mechanicName;

    public TimeSlotResponseDto() {}

    public TimeSlotResponseDto(Long id, LocalDateTime startTime, LocalDateTime endTime, 
                              boolean available, Long mechanicId, String mechanicName) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
        this.mechanicId = mechanicId;
        this.mechanicName = mechanicName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }
}
