package com.anykeysolutions.dto;

import com.anykeysolutions.entity.ServiceType;

import java.util.List;

public class MechanicResponseDto {
    private Long id;
    private String name;
    private List<ServiceType> skills;

    public MechanicResponseDto() {}

    public MechanicResponseDto(Long id, String name, List<ServiceType> skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
