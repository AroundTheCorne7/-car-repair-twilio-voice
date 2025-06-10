package com.anykeysolutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mechanics")
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mechanic name is required")
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ServiceType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "mechanic_skills", joinColumns = @JoinColumn(name = "mechanic_id"))
    @Column(name = "skill")
    @NotEmpty(message = "Mechanic must have at least one skill")
    private List<ServiceType> skills = new ArrayList<>();

    @OneToMany(mappedBy = "mechanic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TimeSlot> timeSlots = new ArrayList<>();

    @OneToMany(mappedBy = "mechanic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    // Constructors
    public Mechanic() {}

    public Mechanic(String name, List<ServiceType> skills) {
        this.name = name;
        this.skills = skills != null ? new ArrayList<>(skills) : new ArrayList<>();
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
        this.skills = skills != null ? new ArrayList<>(skills) : new ArrayList<>();
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    // Helper methods
    public boolean hasSkill(ServiceType serviceType) {
        return skills.contains(serviceType);
    }

    public void addSkill(ServiceType serviceType) {
        if (!skills.contains(serviceType)) {
            skills.add(serviceType);
        }
    }

    public void removeSkill(ServiceType serviceType) {
        skills.remove(serviceType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mechanic mechanic = (Mechanic) o;
        return Objects.equals(id, mechanic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Mechanic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", skills=" + skills +
                '}';
    }
}
