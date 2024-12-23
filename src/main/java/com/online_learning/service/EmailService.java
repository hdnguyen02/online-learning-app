package com.online_learning.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final String emailFrom = "hdnguyen7702@gmail.com";

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendOTP(String toEmail, String subject, String contentOtp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("[Online Learning] Mã xác thực (OTP) của bạn");
        message.setTo(toEmail);
        String content = String.format(
                "Kính chào,\n\n"
                        + "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi. Dưới đây là mã xác thực (OTP) của bạn:\n\n"
                        + "Mã OTP: %s\n\n"
                        + "Mã này sẽ hết hạn sau 5 phút. Vui lòng không chia sẻ mã này với bất kỳ ai để đảm bảo an toàn tài khoản của bạn.\n\n"
                        + "Nếu bạn không yêu cầu mã OTP này, vui lòng bỏ qua email này hoặc liên hệ với đội ngũ hỗ trợ của chúng tôi qua email: hdnguyen7702@gmail.com.\n\n"
                        + "Trân trọng,\n"
                        + "Đội ngũ hỗ trợ Online Learning",
                contentOtp);
        message.setText(content);
        message.setFrom("no-reply@onlinelearning.com");
        mailSender.send(message);
    }

}
