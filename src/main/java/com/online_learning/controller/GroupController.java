package com.online_learning.controller;

import com.online_learning.dto.group.GroupResponse;
import com.online_learning.dto.group.GroupRequest;
import com.online_learning.dto.group.UserGroupRequest;
import com.online_learning.entity.User;
import com.online_learning.dto.Response;
import com.online_learning.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/groups")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getGroups() {

        Response response = Response.builder()
                .message("Query successful")
                .data(groupService.getGroups())
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/groups/common-decks/{id}/clone")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> cloneCommonDeck(@PathVariable long id) throws Exception {
        groupService.cloneDeck(id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/groups/{idGroup}/join")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> joinGroup(@PathVariable Long idGroup) throws Exception {
        Response response = Response.builder()
                .message("Query successful")
                .data(groupService.joinGroup(idGroup))
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("groups/{id}/out")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> outGroup(@PathVariable long id) throws Exception {
        groupService.outGroup(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @GetMapping("/groups/global")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getGlobalGroups() {
        Response response = Response.builder()
                .message("Query successful")
                .data(groupService.getGroupsGlobalV2())
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/groups")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {

        Response response = new Response();

        response.setSuccess(true);
        response.setMessage("Created successful");
        response.setData(groupService.createGroup(groupRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/groups/{id}")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody GroupRequest groupRequest) {

        Response response = new Response();
        response.setMessage("Updated successful");
        response.setData(groupService.updateGroup(id, groupRequest));
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/groups/{id}")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getGroupById(@PathVariable long id) {
        Response response = new Response();

        GroupResponse groupResponse = groupService.getGroupById(id);

        response.setMessage("Query successful");
        response.setData(groupResponse);
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // tính tới chuyện phân quyền sau.

    @PostMapping("/groups/delete-user")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> deleteUserGroup(@RequestBody UserGroupRequest userGroupRequest) {
        Response response = new Response();
        response.setData(groupService.deleteUserGroupById(userGroupRequest));
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/groups/{id}/invite")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> inviteUserGroup(
            @PathVariable Long id, @RequestParam String email) throws Exception {
        Response response = new Response();


        // tìm kiếm user đó check xem có quyền truy cập hoạt động nhóm không
  

        response.setMessage("Bạn đã gửi lời mời thành công");
        response.setData(groupService.inviteUserGroup(id, email));
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/groups/owner")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getGroupsByUser() {

        List<GroupResponse> groupResponses = groupService.getGroupsByOwner();

        Response response = new Response();
        response.setSuccess(true);
        response.setData(groupResponses);
        response.setMessage("Query successful");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/groups/attendance")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getGroupsByAttendance() {

        Response response = new Response();

        response.setData(groupService.getGroupsByAttendance());
        response.setSuccess(true);
        response.setMessage("Query successful");

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/groups/{id}")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> deleteGroupById(@PathVariable Long id) {

        Response response = new Response();
        response.setMessage("Deleted successfully");
        response.setData(groupService.deleteGroupById(id));
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
