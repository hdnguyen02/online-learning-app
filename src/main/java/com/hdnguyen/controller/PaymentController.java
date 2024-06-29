package com.hdnguyen.controller;

import com.hdnguyen.dao.UserDao;
import com.hdnguyen.entity.Role;
import com.hdnguyen.entity.User;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.PaymentService;
import com.hdnguyen.util.Helper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserDao userDao;
    private final Helper helper;
    @GetMapping("payment")
    public ResponseEntity<?> getPayment(HttpServletRequest request) {

        System.out.println(helper.getEmailUser());
        String email = request.getParameter("email");
        String urlPayment = paymentService.createVnPayPayment(email, request);

        Response response = new Response();
        response.setSuccess(true);
        response.setData(urlPayment);
        response.setMessage("Tiến hành thanh toán");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("payment-callback")
    public ResponseEntity<?> paymentCallbackHandler(HttpServletRequest request) {

        String email = request.getParameter("email");
        User user = userDao.findById(email).orElseThrow();

        user.getRoles().add(Role.builder().name("TEACHER").build());
        userDao.save(user);

        String status = request.getParameter("vnp_ResponseCode");


        if (status.equals("00")) {

            return ResponseEntity.status(HttpStatus.OK)
                    .body(Response.builder().success(true).message("Thanh toán thành công").data(null).build());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder().success(false).message("Thanh toán thất bại").data(null).build());
        }
    }
}

