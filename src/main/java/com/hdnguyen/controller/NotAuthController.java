package com.hdnguyen.controller;

import com.hdnguyen.dto.Response;
import com.hdnguyen.service.GroupService;
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
        response.setMessage("Bạn đã tham gia lớp học");

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // bỏ vào đây đăng nhập + đăng ký.

}
