package com.online_learning.controller;

import com.online_learning.dto.Response;
import com.online_learning.service.EmailService;
import com.online_learning.service.GroupService;
import com.online_learning.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class NotAuthController {

    private final GroupService groupService;



    @GetMapping("/groups/{id}/active")
    public ResponseEntity<?> addUserGroup(@PathVariable(name = "id") Long id,
                                          @RequestParam String token){
        Response response = new Response();

        response.setData(groupService.activeUserGroup(id, token));
        response.setSuccess(true);
        response.setMessage("You have joined the class");

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
