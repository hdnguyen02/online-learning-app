package com.online_learning.controller;

import com.online_learning.service.EmailService;
import com.online_learning.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class OtpController {

    private final OtpService otpService;
    private final EmailService emailService;

    @PostMapping("/otp/send")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        String contentOtp = otpService.generateOTP(email);
        emailService.sendOTP(email, "OTP Verification", contentOtp);
        return ResponseEntity.ok("OTP sent to email: " + email);
    }
}
