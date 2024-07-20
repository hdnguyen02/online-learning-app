package com.hdnguyen.controller;

import com.hdnguyen.dao.UserDao;
import com.hdnguyen.entity.Role;
import com.hdnguyen.entity.User;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.InvoiceService;
import com.hdnguyen.service.PaymentService;
import com.hdnguyen.service.UserService;
import com.hdnguyen.util.Helper;
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
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final InvoiceService invoiceService;
    private final UserService userService;

    @GetMapping("payment")
    public ResponseEntity<?> getPayment(HttpServletRequest request) {

        String urlPayment = paymentService.createVnPayPayment(request);

        Response response = new Response();
        response.setSuccess(true);
        response.setData(urlPayment);
        response.setMessage("Proceed to payment");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("payment-callback")
    @Transactional
    public ResponseEntity<?> paymentCallbackHandler(HttpServletRequest request) throws ParseException {

        String emailUser = request.getParameter("email");
        String vnpResponseCode = request.getParameter("vnp_ResponseCode");
        BigDecimal vnpAmount = new BigDecimal(request.getParameter("vnp_Amount"));
        String vnpBankCode = request.getParameter("vnp_BankCode");
        String vnpCardType = request.getParameter("vnp_CardType");
        String vnpOrderInfo = request.getParameter("vnp_OrderInfo");
        Date vnpPayDate = Helper.convertTimestampToDate(request.getParameter("vnp_PayDate")) ;

        if (vnpResponseCode.equals("00")) {
            try {
                invoiceService.createInvoice(emailUser, vnpResponseCode, vnpAmount, vnpBankCode, vnpCardType, vnpOrderInfo, vnpPayDate);
                userService.updateUserWithRoleTeacher(emailUser);
            }
            catch (Exception e) {
                System.out.println("Lỗi thanh toán: " + e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(Response.builder().success(true).message("Payment success").data(null).build());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder().success(false).message("Payment failed").data(null).build());
        }
    }
}

