package com.online_learning.service;

import com.online_learning.util.Helper;
import com.online_learning.dao.TokenDao;
import com.online_learning.dao.UserDao;
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
    private final UserDao userDao;
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

        Role role = new Role("STUDENT");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User userCheck =  userDao.findById(signUpRequest.getEmail()).orElse(null);
        if (userCheck != null) throw new Exception("Email đã được sử dụng!");
        if (signUpRequest.getPassword().length() < 6) throw new Exception("Password cần phải >= 6 ký tự!");
        var user = User.builder()
                .email(email)
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .password(passwordEncoder.encode(password))
                .createAt(new Date())
                .isEnabled(true)
                .roles(roles)
                .build();

        String accessToken = jwtService.generateToken(userDao.save(user), isRemember);
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
        User user = userDao.findById(email).orElseThrow();
        String accessToken = jwtService.generateToken(user, isRemember);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .user(new UserResponse(user))
                .build();
    }

    // viết 1 hàm khởi tạo tài khoản khi trong table chưa có tài khoản nào
    public void initUser() {
        List<User> users = userDao.findAll();
        if (!users.isEmpty()) return;
        // thêm người dùng và

        Set<Role> rolesStudent = new HashSet<>();
        rolesStudent.add(new Role("STUDENT"));

        User student = User.builder()
                .email("n20dccn047@student.ptithcm.edu.vn")
                .firstName("Nguyen Tien")
                .lastName("Dung")
                .password(passwordEncoder.encode("123456"))
                .createAt(new Date())
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
                .createAt(new Date())
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
                .createAt(new Date())
                .roles(rolesAdmin)
                .isEnabled(true)
                .build();

        userDao.save(student);
        userDao.save(teacher);
        userDao.save(admin);
    }

}
