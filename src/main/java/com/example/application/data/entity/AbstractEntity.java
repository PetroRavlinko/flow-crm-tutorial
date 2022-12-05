package com.example.application.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @HashCodeExclude
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private long createdAt;

    @HashCodeExclude
    @LastModifiedDate
    private long modifiedAt;

    @HashCodeExclude
    @CreatedBy
    private String createdBy;

    @HashCodeExclude
    @LastModifiedBy
    private String modifiedBy;
}
