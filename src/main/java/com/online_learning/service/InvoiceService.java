package com.online_learning.service;

import com.online_learning.dao.InvoiceDao;
import com.online_learning.dao.UserRepository;
import com.online_learning.dto.invoice.InvoiceResponse;
import com.online_learning.entity.Invoice;
import com.online_learning.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceDao invoiceDao;
    private final UserRepository userRepository;

    public void createInvoice(String emailUser, String vnpResponseCode, BigDecimal vnpAmount,
            String vnpBankCode, String vnpCardType, String vnpOrderInfo, Date vnpPayDate) {

        User user = userRepository.findByEmail(emailUser).orElseThrow();
        Invoice invoice = Invoice.builder()
                .user(user)
                .vnpResponseCode(vnpResponseCode)
                .vnpAmount(BigDecimal.valueOf(299000L))
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
        calendar.add(Calendar.MONTH, -6); // Lùi lại 6 tháng thay vì 1 năm
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

    // khởi tạo invoice
    public void initInvoice() throws Exception {
        if (!invoiceDao.findAll().isEmpty())
            return;

        List<Invoice> invoices = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Random random = new Random();

        // tìm kiếm 1 user.
        User user = userRepository.findByEmail("hdnguyen7702@gmail.com").orElse(null);
        if (user == null) {
            throw new Exception("Not found user with email: hdnguyen7702@gmail.com");
        }
        for (int i = 0; i < 12; i++) {
            LocalDateTime payDate = now.minusMonths(i);
            Invoice invoice = Invoice.builder()
                    .user(user)
                    .vnpResponseCode("00")
                    .vnpAmount(BigDecimal.valueOf(1000 + random.nextInt(9000)))
                    .vnpBankCode("VCB")
                    .vnpCardType("ATM")
                    .vnpOrderInfo("Order " + (i + 1))
                    .vnpPayDate(Date.from(payDate.atZone(ZoneId.systemDefault()).toInstant()))
                    .build();
            invoices.add(invoice);

            invoiceDao.saveAll(invoices);
        }

    }

}
