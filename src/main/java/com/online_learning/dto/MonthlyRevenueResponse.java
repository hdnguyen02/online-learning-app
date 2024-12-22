package com.online_learning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlyRevenueResponse {
    private int month;
    private int year;
    private BigDecimal revenue;
}
