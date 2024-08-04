package com.online_learning.controller;


import com.online_learning.dto.Response;
import com.online_learning.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping("/student/assignments")
    public ResponseEntity<?> getAssignments(@RequestParam(name = "id-group") Long idGroup
    ) throws Exception {

        Response response = new Response();
        response.setData(assignmentService.getStudentAssignments(idGroup));
        response.setSuccess(true);
        response.setMessage("Query successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/student/assignments/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable Long id) throws Exception {

        Response response = new Response();
        response.setData(assignmentService.getStudentAssignment(id));
        response.setSuccess(true);
        response.setMessage("Query successful");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
