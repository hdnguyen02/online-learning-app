package com.online_learning.controller;

import com.online_learning.dto.Response;
import com.online_learning.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class UserGroupController {

    private final UserGroupService userGroupService;

    @DeleteMapping("/user-groups/{idUserGroup}")
    public ResponseEntity<?> deleteUserGroup(@PathVariable Long idUserGroup) {

        Response response = Response.builder()
                .message("Member deleted successfully")
                .data(userGroupService.deleteUserGroup(idUserGroup))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
