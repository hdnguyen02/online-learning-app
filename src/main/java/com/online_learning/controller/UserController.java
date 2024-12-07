package com.online_learning.controller;


import com.online_learning.dto.auth.UserResponse;
import com.online_learning.dto.Response;
import com.online_learning.service.UserService;
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

    @GetMapping("/users")
    public ResponseEntity<Response> getInfoUser(@RequestParam(required = false) Long id) {
        UserResponse userResponse;
        if (id == null) {
            userResponse = userService.getInfoUser();
        }
        else {
            userResponse = userService.getInfoOtherUser(id);
        }
        Response response = new Response(userResponse, "Query successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/info")
    public ResponseEntity<Response> updateUser(@RequestParam (required = false) String  firstName,
                                               @RequestParam (required = false) String lastName,
                                               @RequestParam (required = false) String gender,
                                               @RequestParam (required = false) String phone,
                                               @RequestParam (required = false) String dateOfBirth,
                                               @RequestParam (required = false) MultipartFile avatar) throws IOException {

        UserResponse userResponse = userService.updateUser(firstName, lastName, gender, phone, dateOfBirth, avatar);
        Response response = new Response(userResponse, "Updated successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/password")
    public ResponseEntity<Response> changePW(@RequestBody Map<String, String> maps) {

        String newPW = maps.get("newPassword");
        userService.changePW(newPW);
        Response response = new Response(null, "Updated successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/decks")
    public ResponseEntity<Response> getDeckOfUser(@RequestParam Long id) {

        Response response = Response.builder()
                .data(userService.getDecks(id))
                .message("Query successful")
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
