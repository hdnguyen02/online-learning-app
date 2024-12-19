package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "invoices")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Invoice extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_user", updatable = false)
    private User user;

    @Column(updatable = false)
    private String vnpResponseCode;

    @Column(updatable = false)
    private BigDecimal vnpAmount;

    @Column(updatable = false)
    private String vnpBankCode;

    @Column(updatable = false)
    private String vnpCardType;

    @Column(updatable = false)
    private String vnpOrderInfo;

    @Column(updatable = false)
    private Date vnpPayDate;

}