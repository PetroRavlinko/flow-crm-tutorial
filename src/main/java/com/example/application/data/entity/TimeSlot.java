package com.example.application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
public class TimeSlot extends AbstractEntity {
    @NotEmpty
    private String description = "";
    @Min(0)
    private double hours = .0;
    
}
