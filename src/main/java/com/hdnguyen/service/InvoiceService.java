package com.hdnguyen.service;


import com.hdnguyen.dao.InvoiceDao;
import com.hdnguyen.entity.Invoice;
import com.hdnguyen.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceDao invoiceDao;

    public void createInvoice(String emailUser, String vnpResponseCode, BigDecimal vnpAmount,
                              String vnpBankCode, String vnpCardType, String vnpOrderInfo, Date vnpPayDate
                              ) {

        Invoice invoice = Invoice.builder()
                .user(new User(emailUser))
                .vnpResponseCode(vnpResponseCode)
                .vnpAmount(vnpAmount)
                .vnpBankCode(vnpBankCode)
                .vnpCardType(vnpCardType)
                .vnpOrderInfo(vnpOrderInfo)
                .vnpPayDate(vnpPayDate)
                .build();

        invoiceDao.save(invoice);

    }

}
