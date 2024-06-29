package com.hdnguyen.controller;

import com.hdnguyen.dto.group.GroupDto;
import com.hdnguyen.dto.group.GroupRequest;
import com.hdnguyen.dto.group.UserGroupRequest;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.GroupService;
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
                .message("Đã tham gia lớp học ")
                .data(groupService.joinGroup(idGroup)) // thêm vào danh sách lớp học của người dùng.
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/global/groups")
    public ResponseEntity<?> getGlobalGroups(@RequestParam(required = false) String searchTerm) {

        // lấy ra toàn bộ lớp học => nếu không search trả ra toàn bộ.
        List<GroupDto> groupDtos;
        if (searchTerm != null) groupDtos = groupService.searchGlobalGroups(searchTerm);
        else groupDtos = groupService.getGlobalGroups();

        Response response = Response.builder()
                .message("Truy vấn danh sách tìm kiếm")
                .data(groupDtos)
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // tạo lớp với role giáo viên
    @PostMapping("/groups")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest){

        Response response = new Response();

        response.setData(groupService.createGroup(groupRequest));
        response.setSuccess(true);
        response.setMessage("Tạo class thành công");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // hiệu chỉnh lớp với role giáo viên
    @PutMapping("/groups/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> updateGroup(@PathVariable Long id , @RequestBody GroupRequest groupRequest) {

        Response responseData = new Response();
        responseData.setData(groupService.updateGroup(id, groupRequest));
        responseData.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    // chi tiết của 1 lớp học
    @GetMapping("/groups/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable(name = "id") Long id) {
        Response response = new Response();

        GroupDto groupDto = groupService.getGroupById(id);
        response.setData(groupDto);
        response.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/groups/delete-user")
    public ResponseEntity<?> deleteUserGroup(@RequestBody UserGroupRequest userGroupRequest) {
        // Xóa thành viên khỏi lớp học.
        Response responseData = new Response();
        responseData.setData(groupService.deleteUserGroupById(userGroupRequest));
        responseData.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/groups/{id}/invite")
    public ResponseEntity<?> inviteUserGroup(
            @PathVariable Long id, @RequestParam(name = "email-user") String emailUser) {

        Response responseData = new Response();

        responseData.setData(groupService.inviteUserGroup(id, emailUser));
        responseData.setSuccess(true);
        responseData.setMessage("Gửi mail đến người dùng thành công");

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping("/groups/owner")
    public ResponseEntity<?> getGroupsByUser() {
        Response responseData = new Response();
        List<GroupDto> groupDtos = groupService.getGroupsByOwner();
        responseData.setSuccess(true);
        responseData.setData(groupDtos);
        responseData.setMessage("Truy vấn thành công");
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping("/groups/attendance")
    public ResponseEntity<?> getGroupsByAttendance(){
        Response responseData = new Response();

        responseData.setData(groupService.getGroupsByAttendance());
        responseData.setSuccess(true);
        responseData.setMessage("Truy vấn thành công");

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<?> deleteGroupById(@PathVariable Long id) {
        Response responseData = new Response();
        responseData.setData(groupService.deleteGroupById(id));
        responseData.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
