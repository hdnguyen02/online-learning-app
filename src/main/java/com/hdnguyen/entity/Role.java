package com.hdnguyen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Table(name="roles")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Role {
    @Id
    @Column(length = 50)
    private String name;


    public Role(String name) {
        this.name = name;
    }

}