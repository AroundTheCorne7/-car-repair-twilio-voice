package com.anykeysolutions.service;

import com.anykeysolutions.dto.CreateMechanicRequest;
import com.anykeysolutions.dto.UpdateMechanicRequest;
import com.anykeysolutions.entity.Mechanic;
import com.anykeysolutions.entity.ServiceType;
import com.anykeysolutions.repository.MechanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    @Autowired
    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    public Optional<Mechanic> getMechanicById(Long id) {
        return mechanicRepository.findById(id);
    }

    public Mechanic createMechanic(CreateMechanicRequest request) {
        Mechanic mechanic = new Mechanic(request.getName(), request.getSkills());
        return mechanicRepository.save(mechanic);
    }

    public Optional<Mechanic> updateMechanic(Long id, UpdateMechanicRequest request) {
        return mechanicRepository.findById(id)
                .map(mechanic -> {
                    if (request.getName() != null && !request.getName().trim().isEmpty()) {
                        mechanic.setName(request.getName().trim());
                    }
                    if (request.getSkills() != null && !request.getSkills().isEmpty()) {
                        mechanic.setSkills(request.getSkills());
                    }
                    return mechanicRepository.save(mechanic);
                });
    }

    public boolean deleteMechanic(Long id) {
        if (mechanicRepository.existsById(id)) {
            mechanicRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Mechanic> getMechanicsBySkill(ServiceType serviceType) {
        return mechanicRepository.findBySkillsContaining(serviceType);
    }

    public List<Mechanic> searchMechanicsByName(String name) {
        return mechanicRepository.findByNameContainingIgnoreCase(name);
    }

    public boolean mechanicHasSkill(Long mechanicId, ServiceType serviceType) {
        return mechanicRepository.findById(mechanicId)
                .map(mechanic -> mechanic.hasSkill(serviceType))
                .orElse(false);
    }
}
