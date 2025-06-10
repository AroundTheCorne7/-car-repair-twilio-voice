package com.anykeysolutions.dto;

import com.anykeysolutions.entity.ServiceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CreateBookingRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9\\s\\-()]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    private String description;

    @NotNull(message = "Service type is required")
    private ServiceType serviceType;

    @NotNull(message = "Mechanic ID is required")
    private Long mechanicId;

    @NotNull(message = "Time slot ID is required")
    private Long timeSlotId;

    // Constructors
    public CreateBookingRequest() {}

    public CreateBookingRequest(String customerName, String phoneNumber, String description,
                               ServiceType serviceType, Long mechanicId, Long timeSlotId) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.serviceType = serviceType;
        this.mechanicId = mechanicId;
        this.timeSlotId = timeSlotId;
    }

    // Getters and Setters
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

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }
}
