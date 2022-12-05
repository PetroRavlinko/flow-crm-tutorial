package com.example.application.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    @NotBlank
    private String username;
    @Size(min = 8, max = 64, message = "Password must be 8-64 char long")
    private String password;
    private boolean isActive;
    private String roles;
}
