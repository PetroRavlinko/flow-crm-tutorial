package com.example.application.data.repository;

import com.example.application.data.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimeSlotRepository  extends JpaRepository<TimeSlot, Long> {

    @Query("select sum(ts.hours) from TimeSlot ts")
    Double totalHours();
    @Query("select sum(ts.hours) from TimeSlot ts where ts.task is null")
    Double totalUnassignedHours();
}
