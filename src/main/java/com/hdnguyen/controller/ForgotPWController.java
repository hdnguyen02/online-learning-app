package com.hdnguyen.controller;


import com.hdnguyen.dao.UserDao;
import com.hdnguyen.entity.User;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.EmailService;
import com.hdnguyen.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class ForgotPWController {
    private final EmailService emailService;
    private final UserDao userDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/forgot-password")
    public Response forgotPassword(@RequestBody Map<String, String> maps) throws Exception {
        String email = maps.get("email");
        User user = userDao.findById(email).orElse(null);
        if (user == null) throw new Exception("Email không tồn tại!");
        String accessToken = jwtService.generateTokenForgotPassword(email);
        String resetUrl = "http://localhost:5173/reset-password?access-token=" + accessToken;
        emailService.sendEmail(email, "Khôi phục mật khẩu Online Learning", resetUrl);
        return Response.builder()
                .message("Link khôi phục mật khẩu đã được gửi đến email của bạn")
                .data(null)
                .success(true)
                .build();
    }

    @PostMapping("api/v1/reset-password")
    public Response resetPassword(@RequestBody Map<String, String> maps) {
        String newPassword = maps.get("newPassword");
        String accessToken = maps.get("accessToken");
        String email = jwtService.extractUsername(accessToken);
        User user = userDao.findById(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
        return Response.builder()
                .message("Thay đổi mật khẩu thành công")
                .data(null)
                .success(true)
                .build();
    }
}
