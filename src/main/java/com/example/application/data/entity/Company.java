package com.example.application.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
public class Company extends AbstractEntity {
    @Setter
    @NotBlank
    private String name;
    @Formula("(select count(c.id) from Contact c where c.company_id = id)")
    private int employeeCount;
    @Setter
    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Contact> employees = new LinkedList<>();
}
