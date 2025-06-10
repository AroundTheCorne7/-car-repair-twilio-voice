package com.anykeysolutions.controller;

import com.anykeysolutions.dto.CreateMechanicRequest;
import com.anykeysolutions.dto.MechanicResponseDto;
import com.anykeysolutions.dto.UpdateMechanicRequest;
import com.anykeysolutions.entity.Mechanic;
import com.anykeysolutions.entity.ServiceType;
import com.anykeysolutions.service.MechanicService;
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
@RequestMapping("/api/mechanics")
@Tag(name = "Mechanics", description = "Mechanic management operations")
public class MechanicController {

    private final MechanicService mechanicService;

    @Autowired
    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @GetMapping
    @Operation(summary = "Get all mechanics", description = "Retrieve a list of all mechanics")
    public ResponseEntity<List<MechanicResponseDto>> getAllMechanics() {
        List<Mechanic> mechanics = mechanicService.getAllMechanics();
        List<MechanicResponseDto> mechanicDtos = mechanics.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mechanicDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get mechanic by ID", description = "Retrieve a specific mechanic by their ID")
    public ResponseEntity<MechanicResponseDto> getMechanicById(
            @Parameter(description = "Mechanic ID") @PathVariable Long id) {
        return mechanicService.getMechanicById(id)
                .map(mechanic -> ResponseEntity.ok(convertToDto(mechanic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new mechanic", description = "Create a new mechanic with skills")
    public ResponseEntity<MechanicResponseDto> createMechanic(
            @Valid @RequestBody CreateMechanicRequest request) {
        try {
            Mechanic createdMechanic = mechanicService.createMechanic(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdMechanic));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update mechanic", description = "Update mechanic's name or skills")
    public ResponseEntity<MechanicResponseDto> updateMechanic(
            @Parameter(description = "Mechanic ID") @PathVariable Long id,
            @RequestBody UpdateMechanicRequest request) {
        try {
            return mechanicService.updateMechanic(id, request)
                    .map(mechanic -> ResponseEntity.ok(convertToDto(mechanic)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete mechanic", description = "Delete a mechanic by ID")
    public ResponseEntity<Void> deleteMechanic(
            @Parameter(description = "Mechanic ID") @PathVariable Long id) {
        if (mechanicService.deleteMechanic(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-skill")
    @Operation(summary = "Get mechanics by skill", description = "Retrieve mechanics who have a specific skill")
    public ResponseEntity<List<MechanicResponseDto>> getMechanicsBySkill(
            @Parameter(description = "Service type skill") @RequestParam ServiceType skill) {
        List<Mechanic> mechanics = mechanicService.getMechanicsBySkill(skill);
        List<MechanicResponseDto> mechanicDtos = mechanics.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mechanicDtos);
    }

    @GetMapping("/search")
    @Operation(summary = "Search mechanics by name", description = "Search for mechanics by name (case-insensitive)")
    public ResponseEntity<List<MechanicResponseDto>> searchMechanicsByName(
            @Parameter(description = "Name to search for") @RequestParam String name) {
        List<Mechanic> mechanics = mechanicService.searchMechanicsByName(name);
        List<MechanicResponseDto> mechanicDtos = mechanics.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mechanicDtos);
    }

    private MechanicResponseDto convertToDto(Mechanic mechanic) {
        return new MechanicResponseDto(
                mechanic.getId(),
                mechanic.getName(),
                mechanic.getSkills()
        );
    }
}
