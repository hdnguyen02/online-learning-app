package com.hdnguyen.controller;


import com.hdnguyen.util.Helper;
import com.hdnguyen.dto.deck.DetailDeckResponse;
import com.hdnguyen.dto.deck.DeckResponse;
import com.hdnguyen.dto.deck.DeckRequest;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class DeckController {

    private final DeckService deckService;


    // tạo thêm hàm clone + lấy ra bộ thẻ


    @GetMapping("/global/decks")
    public ResponseEntity<?> getGlobalDecks(@RequestParam(required = false) String searchTerm) {

        List<DeckResponse> deckResponses;
        if (searchTerm != null) deckResponses = deckService.searchGlobalDecks(searchTerm);
        else deckResponses = deckService.getGlobalDecks();

        Response response = Response.builder()
                .message("Danh sách bộ thẻ")
                .data(deckResponses)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // clone bộ thẻ.
    @PostMapping("/decks/{idDeck}/clone")
    public ResponseEntity<?> cloneDeck(@PathVariable Long idDeck) {

        boolean result = deckService.cloneDeck(idDeck);
        Response response = Response.builder()
                .message("Clone bộ thẻ thành công")
                .data(result)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @GetMapping("/decks")
    public ResponseEntity<Response> getDecks(@RequestParam(required = false) String searchTerm) {
        List<DeckResponse> decksDto;
        if (searchTerm == null) {
            decksDto = deckService.getDesks();
        }
        else {
            decksDto = deckService.searchDecks(searchTerm);
        }
        String message = "Truy vấn danh sách bộ thẻ thành công";
        Response response = new Response(decksDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/decks")
    public ResponseEntity<Response> createDeck(@RequestBody DeckRequest deckRequest) {
        DeckResponse deckResponse = deckService.createDeck(deckRequest);
        String message = "Tạo bộ thẻ thành công";
        Response response = new Response(deckResponse, message, true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/decks/{id}")
    public ResponseEntity<Response> deleteDeck(@PathVariable Long id) {
        DeckResponse deckResponse = deckService.deleteDeck(id);
        String message = "Xóa bộ thẻ thành công";
        Response response = new Response(deckResponse, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/decks/{id}")
    public ResponseEntity<Response> updateDeck(@PathVariable Long id,@RequestBody DeckRequest deckRequest)  {
        DeckResponse deckResponse = deckService.updateDeck(id, deckRequest);
        String message = "Hiệu chỉnh bộ thẻ thành công";
        Response response = new Response(deckResponse, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("decks/{id}")
    public ResponseEntity<Response> getDeckWithId(@PathVariable Long id) {
        DetailDeckResponse deckDto = deckService.getDeckWithId(id);
        String message = "Truy vấn bộ thẻ thành công";
        Response response = new Response(deckDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
