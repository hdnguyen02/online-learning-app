package com.online_learning.controller;


import com.online_learning.dto.Response;
import com.online_learning.dto.auth.UserResponse;
import com.online_learning.entity.Invoice;
import com.online_learning.service.AdminService;
import com.online_learning.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class AdminController {

    private final AdminService adminService;
    private final InvoiceService invoiceService;


    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers() throws Exception {

        List<UserResponse> userResponses = adminService.getUsers();

        Response response = Response.builder()
                .data(userResponses)
                .message("Query successful")
                .success(true)
                .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editUser(@RequestParam Boolean isEnabled, @RequestParam String emailUser) {
        adminService.editUser(isEnabled, emailUser);
        Response response = Response.builder()
                .data(null)
                .message("Update successful")
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin/invoices")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getInvoices() {
        Response response = Response.builder()
                .data(invoiceService.getInvoices())
                .message("Query successful")
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStatistics() {

        List<Invoice> invoices = invoiceService.getInvoicesForLast12Months();

        Response response = Response.builder()
                .data(invoiceService.getMonthlyRevenue(invoices))
                .message("Query successful")
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // thông số chung
    @GetMapping("/admin/common-statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCommonStatistics() {

        Response response = Response.builder()
                .data(adminService.getCommonStatistics())
                .message("Query successful")
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
