package com.hdnguyen.controller;


import com.hdnguyen.dto.Response;
import com.hdnguyen.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/student")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping("/assignments")
    public ResponseEntity<?> getAssignments(@RequestParam(name = "id-group") Long idGroup
    ) throws Exception {

        Response response = new Response();
        response.setData(assignmentService.getStudentAssignments(idGroup));
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable Long id) throws Exception {

        Response response = new Response();
        response.setData(assignmentService.getStudentAssignment(id));
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
