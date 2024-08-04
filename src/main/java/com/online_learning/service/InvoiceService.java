package com.online_learning.service;


import com.online_learning.dao.InvoiceDao;
import com.online_learning.dto.invoice.InvoiceResponse;
import com.online_learning.entity.Invoice;
import com.online_learning.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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


    public List<Invoice> getInvoicesForLast12Months() {
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();

        calendar.add(Calendar.YEAR, -1);
        Date startDate = calendar.getTime();

        return invoiceDao.findInvoicesByVnpPayDateRange(startDate, endDate);
    }

    public List<Map<String, Object>> getMonthlyRevenue(List<Invoice> invoices) {
        Map<String, BigDecimal> revenueMap = new TreeMap<>();

        for (Invoice invoice : invoices) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(invoice.getVnpPayDate());
            String monthYear = String.format("%d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
            revenueMap.put(monthYear, revenueMap.getOrDefault(monthYear, BigDecimal.ZERO).add(invoice.getVnpAmount()));
        }

        List<Map<String, Object>> revenueList = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : revenueMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", entry.getKey());
            map.put("revenue", entry.getValue());
            revenueList.add(map);
        }
        return revenueList;
    }


    public List<InvoiceResponse> getInvoices() {
        return invoiceDao.findAll().stream().map(InvoiceResponse::new).toList();
    }

}
