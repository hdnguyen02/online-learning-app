package com.online_learning.dto.invoice;

import com.online_learning.dto.auth.UserResponse;
import com.online_learning.entity.Invoice;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceResponse {

    private Long id;
    private UserResponse user;
    private String vnpResponseCode;
    private BigDecimal vnpAmount;
    private String vnpBankCode;
    private String vnpCardType;
    private String vnpOrderInfo;
    private Date vnpPayDate;

    public InvoiceResponse(Invoice invoice) {
        this.id = invoice.getId();
        this.user = new UserResponse(invoice.getUser());
        this.vnpResponseCode = invoice.getVnpResponseCode();
        this.vnpAmount = invoice.getVnpAmount();
        this.vnpBankCode = invoice.getVnpBankCode();
        this.vnpCardType = invoice.getVnpCardType();
        this.vnpOrderInfo = invoice.getVnpOrderInfo();
        this.vnpPayDate = invoice.getVnpPayDate();
    }
}
