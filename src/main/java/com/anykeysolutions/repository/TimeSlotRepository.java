package com.anykeysolutions.repository;

import com.anykeysolutions.entity.Mechanic;
import com.anykeysolutions.entity.ServiceType;
import com.anykeysolutions.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByAvailableTrue();

    List<TimeSlot> findByMechanicAndAvailableTrue(Mechanic mechanic);

    @Query("SELECT ts FROM TimeSlot ts WHERE ts.available = true AND ts.mechanic IN " +
           "(SELECT m FROM Mechanic m JOIN m.skills s WHERE s = :serviceType)")
    List<TimeSlot> findAvailableSlotsByServiceType(@Param("serviceType") ServiceType serviceType);

    @Query("SELECT ts FROM TimeSlot ts WHERE ts.mechanic = :mechanic AND " +
           "((ts.startTime < :endTime AND ts.endTime > :startTime))")
    List<TimeSlot> findOverlappingSlots(@Param("mechanic") Mechanic mechanic,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);

    List<TimeSlot> findByMechanicAndStartTimeBetween(Mechanic mechanic, 
                                                    LocalDateTime startTime, 
                                                    LocalDateTime endTime);

    @Query("SELECT ts FROM TimeSlot ts WHERE ts.startTime >= :startTime AND ts.endTime <= :endTime")
    List<TimeSlot> findByTimeRange(@Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);
}
