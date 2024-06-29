package com.hdnguyen.controller;


import com.hdnguyen.dto.Response;
import com.hdnguyen.service.SubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class SubmitStudentController {

    private final SubmitService submitService;


    // chỗ này là thời hạn nộp bài => bắt thêm trường hợp cần phải đúng thời gian mới được nộp.

    @PostMapping("/submits")
    public ResponseEntity<?> createGroup(@RequestParam Long idGroup,
                                         @RequestParam Long idAssignment,
                                         @RequestParam MultipartFile file
                                         ) throws Exception {


        Response response = new Response();
        submitService.createSubmit(idGroup, idAssignment, file);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
