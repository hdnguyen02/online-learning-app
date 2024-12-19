package com.online_learning.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "opts")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Opt {
}
