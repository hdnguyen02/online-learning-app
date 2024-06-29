package com.hdnguyen.service;


import com.hdnguyen.dao.UserDao;
import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserDao userDao;

    List<UserResponse> getUsers() {
        List<User> users = userDao.findAll();

        return users.stream()
                .map(UserResponse::new).toList();
    }

    // bộ thẻ và thẻ làm trong thống kê

    // lấy thông tin từ hóa đơn





}
