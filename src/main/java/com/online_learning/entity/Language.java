package com.online_learning.entity;


import com.online_learning.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Table(name = "Languages")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Language extends BaseEntity {
    private String code;
    private String nameInternational;
    private String nameVietnamese;
}
