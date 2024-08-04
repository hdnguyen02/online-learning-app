package com.online_learning.controller;


import com.online_learning.dto.deck.DetailDeckResponse;
import com.online_learning.dto.deck.DeckResponse;
import com.online_learning.dto.deck.DeckRequest;
import com.online_learning.dto.Response;
import com.online_learning.service.DeckService;
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


    @GetMapping("/global/decks")
    public ResponseEntity<?> getGlobalDecks(@RequestParam(required = false) String searchTerm) {

        List<DeckResponse> deckResponses;
        if (searchTerm != null) deckResponses = deckService.searchGlobalDecks(searchTerm);
        else deckResponses = deckService.getGlobalDecks();

        Response response = Response.builder()
                .message("Query successful")
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
                .message("Copy the card set successfully")
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
        String message = "Query successful";
        Response response = new Response(decksDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/decks")
    public ResponseEntity<Response> createDeck(@RequestBody DeckRequest deckRequest) {
        DeckResponse deckResponse = deckService.createDeck(deckRequest);
        String message = "Create a successful card set";
        Response response = new Response(deckResponse, message, true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/decks/{id}")
    public ResponseEntity<Response> deleteDeck(@PathVariable Long id) {
        DeckResponse deckResponse = deckService.deleteDeck(id);
        String message = "Deleted successfully";
        Response response = new Response(deckResponse, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/decks/{id}")
    public ResponseEntity<Response> updateDeck(@PathVariable Long id,@RequestBody DeckRequest deckRequest)  {
        DeckResponse deckResponse = deckService.updateDeck(id, deckRequest);
        String message = "Edit card set was successful";
        Response response = new Response(deckResponse, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/decks/{id}")
    public ResponseEntity<Response> getDeckWithId(@PathVariable Long id) {
        DetailDeckResponse deckDto = deckService.getDeckWithId(id);
        String message = "Query successful";
        Response response = new Response(deckDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
