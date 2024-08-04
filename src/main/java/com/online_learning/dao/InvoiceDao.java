package com.online_learning.dao;

import com.online_learning.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InvoiceDao extends JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE i.vnpPayDate BETWEEN :startDate AND :endDate")
    List<Invoice> findInvoicesByVnpPayDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
