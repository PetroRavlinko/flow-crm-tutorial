package com.example.application.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;
}
