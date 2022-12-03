package com.example.application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
public class Task extends AbstractEntity {
    @NotEmpty
    private String name = "";

    @OneToMany(mappedBy = "task")
    @Nullable
    private List<TimeSlot> timeSlots = new LinkedList<>();
}
