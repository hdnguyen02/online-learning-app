package com.online_learning.entity;


import com.online_learning.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Table(name = "otp")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Otp extends BaseEntity {
    private String email;
    private String contentOtp;
    private Date expirationTime;
}
