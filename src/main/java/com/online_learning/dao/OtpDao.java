package com.online_learning.dao;

import com.online_learning.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OtpDao extends JpaRepository<Otp, Long> {
    @Query("SELECT o FROM Otp o WHERE o.email = :email ORDER BY o.createdDate DESC")
    Optional<Otp> findLatestByEmail(@Param("email") String email);
}
