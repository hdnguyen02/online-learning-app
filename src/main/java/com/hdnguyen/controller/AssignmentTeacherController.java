package com.hdnguyen.controller;


import com.hdnguyen.dto.Response;
import com.hdnguyen.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/teacher")
public class AssignmentTeacherController {

    private final AssignmentService assignmentService;


    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/assignments/{id}")
    public ResponseEntity<?> getTeacherAssignment(@PathVariable Long id) throws Exception {

        Response response = Response.builder()
                .data(assignmentService.getTeacherAssignment(id))
                .success(true)
                .message("Truy vấn thành công")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/assignments")
    public ResponseEntity<?> filterCards(@RequestParam String name,
                                         @RequestParam String description,
                                         @RequestParam Long idGroup,
                                         @RequestParam String deadline,
                                         @RequestParam MultipartFile file
    ) throws Exception {

        Date dDeadline = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(deadline);
        boolean result = assignmentService.createAssignment(name, description, idGroup, dDeadline, file);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
