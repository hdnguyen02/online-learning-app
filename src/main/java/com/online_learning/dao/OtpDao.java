package com.online_learning.dao;

import com.online_learning.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OtpDao extends JpaRepository<Otp, Long> {
    List<Otp> findAllByEmail(@Param("email") String email);
}
