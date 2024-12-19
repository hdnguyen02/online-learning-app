package com.online_learning.service;

import com.online_learning.core.EnumRole;
import com.online_learning.util.Helper;
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
    private final Helper helper;
    private final TokenDao tokenDao;


    private void saveToken(String code, User user){
        Token token = Token.builder().code(code).isSignOut(false).user(user).build();
        try {
            tokenDao.save(token);
        }
        catch (Exception e) {
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

        User userCheck =  userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);
        if (userCheck != null) throw new Exception("email has been used!");
        if (signUpRequest.getPassword().length() < 6) throw new Exception("Password needs to be >= 6 characters!");
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
                email, password
        ));
        User user = userRepository.findByEmail(email).orElseThrow();
        String accessToken = jwtService.generateToken(user, isRemember);

        System.out.println(accessToken);
        saveToken(accessToken, user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .user(new UserResponse(user))
                .build();
    }

    public void initUser() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) return;

        Set<Role> rolesStudent = new HashSet<>();
        rolesStudent.add(new Role("STUDENT"));

        User student01 = User.builder()
                .email("n20dccn047@student.ptithcm.edu.vn")
                .firstName("Nguyen Tien")
                .lastName("Dung")
                .password(passwordEncoder.encode("123456"))

                .roles(rolesStudent)
                .isEnabled(true)
                .build();


        User student02 = User.builder()
                .email("hoducnguyen@gmail.com")
                .firstName("Ho Duc")
                .lastName("Nguyen")
                .password(passwordEncoder.encode("123456"))

                .roles(rolesStudent)
                .isEnabled(true)
                .build();

        Set<Role> rolesTeacher = new HashSet<>();
        rolesTeacher.add(new Role("STUDENT"));
        rolesTeacher.add(new Role("TEACHER"));


        User teacher = User.builder()
                .email("hdnguyen7702@gmail.com")
                .firstName("Ho Duc")
                .lastName("Nguyen")
                .password(passwordEncoder.encode("123456"))

                .roles(rolesTeacher)
                .isEnabled(true)
                .build();

        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(new Role("STUDENT"));
        rolesAdmin.add(new Role("TEACHER"));
        rolesAdmin.add(new Role("ADMIN"));

        User admin = User.builder()
                .email("ngunguoi7702@gmail.com")
                .firstName("Ho")
                .lastName("Lan")
                .password(passwordEncoder.encode("123456"))

                .roles(rolesAdmin)
                .isEnabled(true)
                .build();

        userRepository.save(student01);
        userRepository.save(student02);
        userRepository.save(teacher);
        userRepository.save(admin);
    }

}
