package com.anykeysolutions.repository;

import com.anykeysolutions.entity.Booking;
import com.anykeysolutions.entity.Mechanic;
import com.anykeysolutions.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByMechanic(Mechanic mechanic);

    List<Booking> findByServiceType(ServiceType serviceType);

    List<Booking> findByCustomerNameContainingIgnoreCase(String customerName);

    @Query("SELECT b FROM Booking b WHERE b.timeSlot.startTime >= :startTime AND b.timeSlot.endTime <= :endTime")
    List<Booking> findByTimeRange(@Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);

    @Query("SELECT b FROM Booking b WHERE b.createdAt >= :startDate AND b.createdAt <= :endDate")
    List<Booking> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}
