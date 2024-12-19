package com.online_learning.controller;

import com.online_learning.dao.TokenDao;
import com.online_learning.dto.auth.SignInRequest;
import com.online_learning.dto.auth.SignUpRequest;
import com.online_learning.dto.auth.AuthResponse;
import com.online_learning.dto.Response;
import com.online_learning.entity.Token;
import com.online_learning.service.AuthService;
import com.online_learning.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("${system.version}")
public class AuthController {
    private final AuthService authService;
    private final OtpService otpService;
    private final TokenDao tokenDao;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {

        // check otp
        String email = signUpRequest.getEmail();
        String contentOtp = signUpRequest.getContentOtp();

        boolean isValidOtp = otpService.validateOTP(email, contentOtp);
        if (!isValidOtp)
            throw new Exception("Invalid or expired OTP!");

        AuthResponse auth = authService.signUp(signUpRequest);
        String message = "Signed Up successfully";
        return new ResponseEntity<>(new Response(auth, message, true), HttpStatus.OK);
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        AuthResponse auth = authService.signIn(signInRequest);
        String message = "Logged in successfully";
        return new ResponseEntity<>(new Response(auth, message, true), HttpStatus.OK);
    }

    @PostMapping("/auth/sign-out")
    public ResponseEntity<String> signOut(HttpServletRequest request) {
        final String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header missing or invalid.");
        }

        String accessToken = authorization.substring(7);
        Token token = tokenDao.findByCode(accessToken).orElse(null);

        if (token == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found.");

        token.setIsSignOut(true);
        tokenDao.save(token);

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Successfully signed out");
    }

}
