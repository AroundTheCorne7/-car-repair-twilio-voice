package com.anykeysolutions.dto;

import com.anykeysolutions.entity.ServiceType;

import java.util.List;

public class UpdateMechanicRequest {

    private String name;
    private List<ServiceType> skills;

    // Constructors
    public UpdateMechanicRequest() {}

    public UpdateMechanicRequest(String name, List<ServiceType> skills) {
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
