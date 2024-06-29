package com.hdnguyen.controller;


import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class UserController {
    private final UserService userService;

    @GetMapping("users/info")
    public ResponseEntity<Response> getInfoUser(@RequestParam(required = false) String email) {
        UserResponse userResponse;
        if (email == null) {
            userResponse = userService.getInfoUser();
        }
        else {
            userResponse = userService.getInfoOtherUser(email);
        }
        Response response = new Response(userResponse, "Truy vấn thành công tin của người dùng", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("users/info")
    public ResponseEntity<Response> updateUser(@RequestParam (required = false) String  firstName,
                                               @RequestParam (required = false) String lastName,
                                               @RequestParam (required = false) String gender,
                                               @RequestParam (required = false) Integer age,
                                               @RequestParam (required = false) String phone,
                                               @RequestParam (required = false) String dateOfBirth,
                                               @RequestParam (required = false) MultipartFile avatar) throws IOException {

        UserResponse userResponse = userService.updateUser(firstName, lastName, gender, age, phone, dateOfBirth, avatar);
        Response response = new Response(userResponse, "Hiệu chỉnh thành công tin của người dùng", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // change pw
    @PutMapping("users/password")
    public ResponseEntity<Response> changePW(@RequestBody Map<String, String> maps) {

        String newPW = maps.get("newPassword");
        userService.changePW(newPW);
        Response response = new Response(null, "Thay đổi mật khẩu thành công", true);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
