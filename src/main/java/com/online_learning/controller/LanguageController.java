package com.online_learning.controller;

import com.online_learning.dao.LanguageDao;
import com.online_learning.dto.LanguageRequest;
import com.online_learning.dto.Response;
import com.online_learning.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/languages")
    public ResponseEntity<?> addLanguage(@RequestBody LanguageRequest languageRequest) {
        languageService.addLanguage(languageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @GetMapping("/languages")
    public ResponseEntity<?> getAllLanguage() throws Exception {
        Response response = Response.builder()
                .message("Query successful")
                .data(languageService.getLanguages())
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
