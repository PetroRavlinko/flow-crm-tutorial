package com.example.application.data.service;

import com.example.application.data.entity.TimeSlot;
import com.example.application.data.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackerService {
    private final TimeSlotRepository timeSlotRepository;

    public TrackerService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<TimeSlot> findAllSlots() {
        return timeSlotRepository.findAll();
    }

    public void save(TimeSlot timeSlot) {
        timeSlotRepository.save(timeSlot);
    }
}
