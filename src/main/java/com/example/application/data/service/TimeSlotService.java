package com.example.application.data.service;

import com.example.application.data.entity.TimeSlot;
import com.example.application.data.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    public List<TimeSlot> findAllSlots() {
        return timeSlotRepository.findAll();
    }

    public void save(TimeSlot timeSlot) {
        timeSlotRepository.save(timeSlot);
    }

    public Double totalHours() {
        return timeSlotRepository.totalHours();
    }

    public Double totalUnassignedHours() {
        return timeSlotRepository.totalUnassignedHours();
    }
}
