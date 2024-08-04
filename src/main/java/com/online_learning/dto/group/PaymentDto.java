package com.online_learning.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    public String code;
    public String message;
    public String paymentUrl;

}
