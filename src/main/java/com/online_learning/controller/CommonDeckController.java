package com.online_learning.controller;


import com.online_learning.dto.Response;
import com.online_learning.dto.common_deckv2.UpdateCommonDeck;
import com.online_learning.dto.deck.CommonDeckRequest;
import com.online_learning.service.CommonDeckService;
import com.online_learning.service.CommonDeckServiceV2;
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
    private final CommonDeckServiceV2 commonDeckServiceV2;


//    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/common-decks")
    public ResponseEntity<?> createCommonDeck(@RequestBody CommonDeckRequest commonDeckRequest) {

        boolean result = commonDeckService.createCommonDeck(commonDeckRequest);
        Response response = Response.builder()
                .message("Created successfully")
                .data(result)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/common-decks")
    public ResponseEntity<?> getCommonDecks(@RequestParam Long idGroup) {

        Response response = Response.builder()
                .message("Query successful")
                .data(commonDeckService.getCommonDecksByIdGroup(idGroup))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // lấy ra chi tiết cái đã
    @GetMapping("/common-decks/{id}")
    public ResponseEntity<?> getCommonDeck(@PathVariable Long id) {

        Response response = Response.builder()
                .message("Query successful")
                .data(commonDeckService.getCommonDeck(id))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // hiệu chỉnh bộ thẻ => đưa lên 2 thông tin

//    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/common-decks/{idCommonDeck}")
    public ResponseEntity<?> editCommonDeck(@PathVariable Long idCommonDeck, @RequestBody CommonDeckRequest commonDeckRequest) {

        Response response = Response.builder()
                .message("Edited successfully")
                .data(commonDeckService.editCommonDeck(idCommonDeck, commonDeckRequest))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/common-decks")
    public ResponseEntity<?> updateCommonDeckV2(@RequestBody UpdateCommonDeck updateCommonDeck) {
        commonDeckServiceV2.updateDeckV2(updateCommonDeck);
        Response response = new Response(true, "Edit common card set was successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



//    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/common-decks/{id}")
    public ResponseEntity<?> deleteCommonDeck(@PathVariable Long id) {

        Response response = Response.builder()
                .message("Deleted successfully")
                .data(commonDeckService.deleteCommonDeck(id))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
