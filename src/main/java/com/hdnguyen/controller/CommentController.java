package com.hdnguyen.controller;

import com.hdnguyen.dto.group.CommentRequest;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class
CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
        Response responseData = new Response();

        responseData.setData(commentService.createComment(commentRequest));
        responseData.setSuccess(true);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    @GetMapping("/comments/groups/{id}")
    public ResponseEntity<?> getCommentByGroupId(@PathVariable Long id) {
        Response responseData = new Response();

        responseData.setData(commentService.getCommentByGroupId(id));
        responseData.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
