package com.example.application.data.repository;

import com.example.application.data.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository  extends JpaRepository<TimeSlot, Long> {
}
