package com.online_learning.service;

import com.online_learning.config.VnpayConfig;
import com.online_learning.util.VnpayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final VnpayConfig vnPayConfig;

    public String createVnPayPayment(HttpServletRequest request) {
        String email = request.getParameter("email");
        long amount = 299000 * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVnPayConfig(email);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VnpayUtil.getIpAddress(request));
        // build query url
        String queryUrl = VnpayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VnpayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnpayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }

}
