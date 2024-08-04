package com.online_learning.controller;


import com.online_learning.dto.Response;
import com.online_learning.service.SubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class SubmitStudentController {

    private final SubmitService submitService;

    @PostMapping("student/submits")
    public ResponseEntity<?> createGroup(@RequestParam Long idGroup,
                                         @RequestParam Long idAssignment,
                                         @RequestParam MultipartFile file
                                         ) throws Exception {

        Response response = new Response();
        submitService.createSubmit(idGroup, idAssignment, file);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
