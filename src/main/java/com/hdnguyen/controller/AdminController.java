package com.hdnguyen.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

        // liệt kê chức năng, hiển thị doanh thu
        // số lượng thành viên đăng ký
        // số bộ thẻ được tạo
        // trả ra toàn bộ người dùng
        @GetMapping("/users")
        public ResponseEntity<?> getUser() throws Exception {

            return new ResponseEntity<>(null, HttpStatus.OK);
        }


}
