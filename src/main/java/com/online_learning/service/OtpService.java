package com.online_learning.service;


import com.online_learning.dao.OtpDao;
import com.online_learning.entity.Otp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpDao otpDao;

    public String generateOTP(String email) {
        String contentOtp = String.format("%06d", new Random().nextInt(999999));
        Date expirationTime = new Date(System.currentTimeMillis() + (5 * 60 * 1000));

        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setContentOtp(contentOtp);
        otp.setExpirationTime(expirationTime);

        otpDao.save(otp);
        return contentOtp;
    }

    public boolean validateOTP(String email, String contentOtp) {
        Optional<Otp> otpOptional = otpDao.findLatestByEmail(email);
        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            if (otp.getContentOtp().equals(contentOtp) && otp.getExpirationTime().after(new Date())) {
                return true;
            }
        }
        return false;
    }
}
