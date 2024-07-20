package com.hdnguyen.service;


import com.hdnguyen.entity.Role;
import com.hdnguyen.util.Helper;
import com.hdnguyen.dao.UserDao;
import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final Helper helper;
    private final FirebaseStorageService firebaseStorageService;
    private final PasswordEncoder passwordEncoder;

    // update thông tin theo.  name, gender, age, phone, dataOfBirth, avatar
    public UserResponse updateUser(String firstName, String lastName, String gender, Integer age, String phone, String dataOfBirth, MultipartFile avatar) throws IOException {
        // kiểm tra thông tin nào cập nhập thì cập nhập. thông tin còn lại hông quan tâm
        User user = helper.getUser();
        if (firstName!= null) {
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        if (gender != null) {
            user.setGender(gender);
        }
        if (age != null) {
            user.setAge(age);
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        if (dataOfBirth != null) {
            user.setDateOfBirth(dataOfBirth);
        }


        if (avatar != null) {
            String url =  firebaseStorageService.save("avatar", avatar);
            user.setAvatar(url);
        }

        return new UserResponse(userDao.save(user));
    }


    public UserResponse getInfoUser() {
        return new UserResponse(helper.getUser());
    }


    // lấy ra thong tin nguoi dung.
    public UserResponse getInfoOtherUser(String email) {
        User user = userDao.findById(email).orElseThrow();
        return new UserResponse(user);
    }


    public User loadUserByEmail(String email) {
        return userDao.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("not found user!"));
    }

    public void changePW(String newPW) {
        User user = helper.getUser();
        user.setPassword(passwordEncoder.encode(newPW));
        userDao.save(user);
    }

    // update role TEACHER
    public void updateUserWithRoleTeacher(String emailUser) {
        User userStored = userDao .findById(emailUser).orElseThrow();
        userStored.getRoles().add(new Role("TEACHER"));
        userDao.save(userStored);
    }
}
