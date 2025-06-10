package com.anykeysolutions.dto;

import com.anykeysolutions.entity.ServiceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateMechanicRequest {

    @NotBlank(message = "Mechanic name is required")
    private String name;

    @NotEmpty(message = "Mechanic must have at least one skill")
    private List<ServiceType> skills;

    // Constructors
    public CreateMechanicRequest() {}

    public CreateMechanicRequest(String name, List<ServiceType> skills) {
        this.name = name;
        this.skills = skills;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceType> getSkills() {
        return skills;
    }

    public void setSkills(List<ServiceType> skills) {
        this.skills = skills;
    }
}
