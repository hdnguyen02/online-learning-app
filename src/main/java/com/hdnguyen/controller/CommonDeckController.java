package com.hdnguyen.controller;


import com.hdnguyen.dto.Response;
import com.hdnguyen.dto.deck.CommonDeckRequest;
import com.hdnguyen.entity.CommonDeck;
import com.hdnguyen.service.CommonDeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class CommonDeckController {

    private final CommonDeckService commonDeckService;


    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/common-decks")
    public ResponseEntity<?> createCommonDeck(@RequestBody CommonDeckRequest commonDeckRequest) {

        boolean result = commonDeckService.createCommonDeck(commonDeckRequest);
        Response response = Response.builder()
                .message("Tạo học phần thành công")
                .data(result)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/common-decks")
    public ResponseEntity<?> getCommonDecks(@RequestParam Long idGroup) {

        Response response = Response.builder()
                .message("Truy vấn common decks")
                .data(commonDeckService.getCommonDecksByIdGroup(idGroup))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // lấy ra chi tiết cái đã
    @GetMapping("/common-decks/{idCommonDeck}")
    public ResponseEntity<?> getCommonDeck(@PathVariable Long idCommonDeck) {

        Response response = Response.builder()
                .message("Truy vấn common deck")
                .data(commonDeckService.getCommonDeck(idCommonDeck))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // hiệu chỉnh bộ thẻ => đưa lên 2 thông tin

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/common-decks/{idCommonDeck}")
    public ResponseEntity<?> editCommonDeck(@PathVariable Long idCommonDeck, @RequestBody CommonDeckRequest commonDeckRequest) {

        Response response = Response.builder()
                .message("Hiệu chỉnh học phần thành công")
                .data(commonDeckService.editCommonDeck(idCommonDeck, commonDeckRequest))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/common-decks/{idCommonDeck}")
    public ResponseEntity<?> deleteCommonDeck(@PathVariable Long idCommonDeck) {

        Response response = Response.builder()
                .message("Xóa học phần thành công")
                .data(commonDeckService.deleteCommonDeck(idCommonDeck))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
