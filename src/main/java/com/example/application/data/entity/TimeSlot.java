package com.example.application.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
public class TimeSlot extends AbstractEntity {
    @NotEmpty
    private String description = "";
    @Min(0)
    private double hours = .0;
    @ManyToOne
    @JoinColumn(name = "taskId")
    @Nullable
    @JsonIgnoreProperties({"timeSlots"})
    private Task task;
    
}
