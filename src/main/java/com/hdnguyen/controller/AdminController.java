package com.hdnguyen.controller;


import com.hdnguyen.dto.Response;
import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/users")
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

    @PutMapping("/users")
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

    public ResponseEntity<?> getInvoices() {
        return null;
    }

}
