package com.online_learning.controller;


import com.online_learning.dao.UserDao;
import com.online_learning.entity.User;
import com.online_learning.dto.Response;
import com.online_learning.service.EmailService;
import com.online_learning.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class ForgotPWController {
    private final EmailService emailService;
    private final UserDao userDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/api/v1/forgot-password")
    public Response forgotPassword(@RequestBody Map<String, String> maps)  {
        String email = maps.get("email");
        User user = userDao.findById(email).orElseThrow();
        String accessToken = jwtService.generateTokenForgotPassword(email);
        String resetUrl = "http://localhost:5173/reset-password?access-token=" + accessToken;
        emailService.sendEmail(email, "Online Learning password recovery", resetUrl);
        return Response.builder()
                .message("Password recovery link has been sent to your email")
                .data(null)
                .success(true)
                .build();
    }

    @PostMapping("/api/v1/reset-password")
    public Response resetPassword(@RequestBody Map<String, String> maps) {
        System.out.println("vào được");
        String newPassword = maps.get("newPassword");
        String accessToken = maps.get("accessToken");
        String email = jwtService.extractUsername(accessToken);
        User user = userDao.findById(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
        return Response.builder()
                .message("Updated successfully")
                .data(null)
                .success(true)
                .build();
    }
}
