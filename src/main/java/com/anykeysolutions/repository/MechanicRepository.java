package com.anykeysolutions.repository;

import com.anykeysolutions.entity.Mechanic;
import com.anykeysolutions.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    @Query("SELECT m FROM Mechanic m JOIN m.skills s WHERE s = :serviceType")
    List<Mechanic> findBySkillsContaining(@Param("serviceType") ServiceType serviceType);

    @Query("SELECT m FROM Mechanic m WHERE m.name LIKE %:name%")
    List<Mechanic> findByNameContainingIgnoreCase(@Param("name") String name);
}
