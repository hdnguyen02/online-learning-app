package com.online_learning.config;

import com.online_learning.util.VNPayUtil;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Configuration
public class VnpayConfig {

    private final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private final String secretKey = "WJUOJ1BON06SPHM766BZ5H4HX2MKFDJW";

    public static String encodeEmail(String email) {
        return Base64.getUrlEncoder().encodeToString(email.getBytes(StandardCharsets.UTF_8));
    }

    public Map<String, String> getVNPayConfig(String email) {
        Map<String, String> vnpParamsMap = new HashMap<>();
        String vnp_Version = "2.1.0";
        vnpParamsMap.put("vnp_Version", vnp_Version);
        String vnp_Command = "pay";
        vnpParamsMap.put("vnp_Command", vnp_Command);
        String vnp_TmnCode = "9JVNKK6K";
        vnpParamsMap.put("vnp_TmnCode", vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef",  VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Payment orders: " +  VNPayUtil.getRandomNumber(8));
        String orderType = "other";
        vnpParamsMap.put("vnp_OrderType", orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        String vnp_ReturnUrl = "http://localhost:8080/api/v1/payment-callback?email=" + email;
        vnpParamsMap.put("vnp_ReturnUrl", vnp_ReturnUrl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 1500);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }
}