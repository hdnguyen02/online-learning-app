package com.hdnguyen.controller;

import com.hdnguyen.dto.auth.SignInRequest;
import com.hdnguyen.dto.auth.SignUpRequest;
import com.hdnguyen.dto.auth.AuthResponse;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("sign-up")
    public  ResponseEntity<?> signUp (@RequestBody SignUpRequest signUpRequest) throws Exception {
        AuthResponse auth = authService.signUp(signUpRequest);
        String message = "Đăng ký tài khoản thành công";
        return new ResponseEntity<>(new Response(auth, message, true), HttpStatus.OK);
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn (@RequestBody SignInRequest signInRequest) {
        AuthResponse auth = authService.signIn(signInRequest);
        String message = "Đăng nhập tài khoản thành công";
        return new ResponseEntity<>(new Response(auth, message, true), HttpStatus.OK);
    }
}
