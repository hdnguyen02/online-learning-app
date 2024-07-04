package com.hdnguyen.controller;

import com.hdnguyen.dao.UserGroupDao;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
