package com.online_learning.service;

import com.online_learning.core.EnumRole;
import com.online_learning.dao.TokenDao;
import com.online_learning.dao.UserRepository;
import com.online_learning.dto.auth.UserResponse;
import com.online_learning.entity.Role;
import com.online_learning.entity.Token;
import com.online_learning.entity.User;
import com.online_learning.dto.auth.SignInRequest;
import com.online_learning.dto.auth.SignUpRequest;
import com.online_learning.dto.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;
        private final TokenDao tokenDao;

        private void saveToken(String code, User user) {
                Token token = Token.builder().code(code).isSignOut(false).user(user).build();
                try {
                        tokenDao.save(token);
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                }

        }

        public AuthResponse signUp(SignUpRequest signUpRequest) throws Exception {
                String email = signUpRequest.getEmail();
                String password = signUpRequest.getPassword();
                Boolean isRemember = signUpRequest.getIsRemember() != null;

                Role role = new Role(EnumRole.STUDENT.toString());
                Set<Role> roles = new HashSet<>();
                roles.add(role);

                User userCheck = userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);
                if (userCheck != null)
                        throw new Exception("email has been used!");
                if (signUpRequest.getPassword().length() < 6)
                        throw new Exception("Password needs to be >= 6 characters!");
                var user = User.builder()
                                .email(email)
                                .firstName(signUpRequest.getFirstName())
                                .lastName(signUpRequest.getLastName())
                                .password(passwordEncoder.encode(password))
                                .isEnabled(true)
                                .roles(roles)
                                .build();

                String accessToken = jwtService.generateToken(userRepository.save(user), isRemember);
                saveToken(accessToken, user);
                return AuthResponse.builder()
                                .accessToken(accessToken)
                                .user(new UserResponse(user))
                                .build();
        }

        public AuthResponse signIn(SignInRequest signInRequest) {
                String email = signInRequest.getEmail();
                String password = signInRequest.getPassword();
                Boolean isRemember = signInRequest.getIsRemember() != null;
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                email, password));
                User user = userRepository.findByEmail(email).orElseThrow();
                String accessToken = jwtService.generateToken(user, isRemember);

                System.out.println(accessToken);
                saveToken(accessToken, user);
                return AuthResponse.builder()
                                .accessToken(accessToken)
                                .user(new UserResponse(user))
                                .build();
        }
}
