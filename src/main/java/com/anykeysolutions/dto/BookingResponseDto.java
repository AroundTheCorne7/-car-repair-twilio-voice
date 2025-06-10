package com.anykeysolutions.dto;

import com.anykeysolutions.entity.ServiceType;

import java.time.LocalDateTime;

public class BookingResponseDto {
    private Long id;
    private String customerName;
    private String phoneNumber;
    private String description;
    private ServiceType serviceType;
    private LocalDateTime createdAt;
    private Long mechanicId;
    private String mechanicName;
    private Long timeSlotId;
    private LocalDateTime appointmentStartTime;
    private LocalDateTime appointmentEndTime;

    public BookingResponseDto() {}

    public BookingResponseDto(Long id, String customerName, String phoneNumber, String description,
                             ServiceType serviceType, LocalDateTime createdAt, Long mechanicId, 
                             String mechanicName, Long timeSlotId, LocalDateTime appointmentStartTime,
                             LocalDateTime appointmentEndTime) {
        this.id = id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.serviceType = serviceType;
        this.createdAt = createdAt;
        this.mechanicId = mechanicId;
        this.mechanicName = mechanicName;
        this.timeSlotId = timeSlotId;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public LocalDateTime getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(LocalDateTime appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public LocalDateTime getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(LocalDateTime appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }
}
