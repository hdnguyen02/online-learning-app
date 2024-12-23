package com.online_learning.core;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;

    public BaseEntity(Long id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }
}
