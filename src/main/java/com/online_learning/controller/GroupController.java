package com.online_learning.controller;

import com.online_learning.dto.group.GroupResponse;
import com.online_learning.dto.group.GroupRequest;
import com.online_learning.dto.group.UserGroupRequest;
import com.online_learning.dto.Response;
import com.online_learning.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/groups/{idGroup}/join")
    public ResponseEntity<?> joinGroup(@PathVariable Long idGroup) throws Exception {
        Response response = Response.builder()
                .message("Query successful")
                .data(groupService.joinGroup(idGroup))
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/global/groups")
    public ResponseEntity<?> getGlobalGroups(@RequestParam(required = false) String searchTerm) {

        List<GroupResponse> groupResponses;
        if (searchTerm != null) groupResponses = groupService.searchGlobalGroups(searchTerm);
        else groupResponses = groupService.getGlobalGroups();

        Response response = Response.builder()
                .message("Query successful")
                .data(groupResponses)
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/groups")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest){

        Response response = new Response();

        response.setSuccess(true);
        response.setMessage("Created successful");
        response.setData(groupService.createGroup(groupRequest));



        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/groups/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> updateGroup(@PathVariable Long id , @RequestBody GroupRequest groupRequest) {

        Response response = new Response();
        response.setMessage("Updated successful");
        response.setData(groupService.updateGroup(id, groupRequest));
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable(name = "id") Long id) {
        Response response = new Response();

        GroupResponse groupResponse = groupService.getGroupById(id);

        response.setMessage("Query successful");
        response.setData(groupResponse);
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/groups/delete-user")
    public ResponseEntity<?> deleteUserGroup(@RequestBody UserGroupRequest userGroupRequest) {
        // Xóa thành viên khỏi lớp học.
        Response response = new Response();
        response.setData(groupService.deleteUserGroupById(userGroupRequest));
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/groups/{id}/invite")
    public ResponseEntity<?> inviteUserGroup(
            @PathVariable Long id, @RequestParam(name = "email") String email) throws Exception {

        Response response = new Response();

        response.setMessage("Sending email to user successfully");
        response.setData(groupService.inviteUserGroup(id, email));
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/groups/owner")
    public ResponseEntity<?> getGroupsByUser() {


        List<GroupResponse> groupResponses = groupService.getGroupsByOwner();

        Response response = new Response();
        response.setSuccess(true);
        response.setData(groupResponses);
        response.setMessage("Query successful");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/groups/attendance")
    public ResponseEntity<?> getGroupsByAttendance(){

        Response response = new Response();

        response.setData(groupService.getGroupsByAttendance());
        response.setSuccess(true);
        response.setMessage("Query successful");

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<?> deleteGroupById(@PathVariable Long id) {

        Response response = new Response();
        response.setMessage("Deleted successfully");
        response.setData(groupService.deleteGroupById(id));
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
