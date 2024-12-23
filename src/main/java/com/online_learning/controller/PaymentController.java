package com.online_learning.controller;

import com.online_learning.dto.Response;
import com.online_learning.service.InvoiceService;
import com.online_learning.service.PaymentService;
import com.online_learning.service.UserService;
import com.online_learning.util.Helper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

@RestController
@CrossOrigin("*")
@RequestMapping("${system.version}")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final InvoiceService invoiceService;
    private final UserService userService;

    @GetMapping("/payment")
    public ResponseEntity<?> getPayment(HttpServletRequest request) {

        String urlPayment = paymentService.createVnPayPayment(request);

        Response response = new Response();
        response.setSuccess(true);
        response.setData(urlPayment);
        response.setMessage("Proceed to payment");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/payment-callback")
    @Transactional
    public ResponseEntity<?> paymentCallbackHandler(HttpServletRequest request) throws ParseException {

        String emailUser = request.getParameter("email");
        String vnpResponseCode = request.getParameter("vnp_ResponseCode");
        BigDecimal vnpAmount = new BigDecimal(request.getParameter("vnp_Amount"));
        String vnpBankCode = request.getParameter("vnp_BankCode");
        String vnpCardType = request.getParameter("vnp_CardType");
        String vnpOrderInfo = request.getParameter("vnp_OrderInfo");
        Date vnpPayDate = Helper.convertTimestampToDate(request.getParameter("vnp_PayDate"));

        if (vnpResponseCode.equals("00")) {
            System.out.println("vnpResponseCode: " + vnpResponseCode);
            try {
                invoiceService.createInvoice(emailUser, vnpResponseCode, vnpAmount, vnpBankCode, vnpCardType,
                        vnpOrderInfo, vnpPayDate);
                userService.updateUserWithRoleGroupActivitiesAccess(emailUser);
                return ResponseEntity.status(HttpStatus.FOUND) // HTTP 302
                        .header("Location", "http://localhost:5173/groups?payment_status=success")
                        .build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", "http://localhost:5173/groups?payment_status=refund")
                        .build();
            }
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:5173/groups?payment_status=fail")
                .build();
    }
}
