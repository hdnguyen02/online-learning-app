package com.online_learning.controller;


import com.online_learning.dto.auth.UserResponse;
import com.online_learning.dto.Response;
import com.online_learning.dto.userv2.UpdateAvatarUser;
import com.online_learning.dto.userv2.UpdatePWUser;
import com.online_learning.dto.userv2.UpdateUser;
import com.online_learning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Response> getInfoUser() {
        UserResponse userResponse;

        userResponse = userService.getProfileUser();

        Response response = new Response(userResponse, "Query successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUser updateUser) {
        boolean result = userService.updateUser(updateUser);
        Response response = new Response(result, "Updated successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/avatar")
    public ResponseEntity<?> updateAvatarUser(@RequestBody UpdateAvatarUser updateAvatarUser) {
        boolean result = userService.updateAvatarUser(updateAvatarUser);
        Response response = new Response(result, "Updated successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/password")
    public ResponseEntity<Response> updatePWUser(@RequestBody UpdatePWUser updatePWUser) throws Exception {
        boolean result = userService.updatePWUser(updatePWUser);
        Response response = new Response(result, "Updated successful", true);
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
