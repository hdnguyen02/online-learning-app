package com.online_learning.controller;

import com.online_learning.dto.card.CardResponse;
import com.online_learning.dto.deck.DetailDeckResponse;
import com.online_learning.dto.deck.DeckResponse;
import com.online_learning.dto.Response;
import com.online_learning.dto.deckv2.*;
import com.online_learning.service.DeckService;
import com.online_learning.service.DeckServiceV2;
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
    private final DeckServiceV2 deckServiceV2;

    @GetMapping("/global/decks")
    public ResponseEntity<?> getGlobalDecks(@RequestParam(required = false) String searchTerm) {

        List<DeckResponse> deckResponses;
        if (searchTerm != null)
            deckResponses = deckService.searchGlobalDecks(searchTerm);
        else
            deckResponses = deckService.getGlobalDecks();

        Response response = Response.builder()
                .message("Query successful")
                .data(deckResponses)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/decks/share")
    public ResponseEntity<?> shareDeck(@RequestBody ShareDeckRequest shareDeckRequest) throws Exception {

        deckServiceV2.shareDeckV2(shareDeckRequest);
        Response response = Response.builder()
                .message("Share the card set successfully")
                .data(null)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // clone bộ thẻ.
    @PostMapping("/decks/{idDeck}/clone")
    public ResponseEntity<?> cloneDeck(@PathVariable Long idDeck) throws Exception {

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
        } else {
            decksDto = deckService.searchDecks(searchTerm);
        }
        String message = "Query successful";
        Response response = new Response(decksDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/decks")
    public ResponseEntity<?> createDeck(@RequestBody CreateDeck createDeck) throws Exception {
        deckServiceV2.createDeckV2(createDeck);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @DeleteMapping("/decks/{id}")
    public ResponseEntity<Response> deleteDeck(@PathVariable Long id) throws Exception {
        DeckResponse deckResponse = deckService.deleteDeck(id);
        String message = "Deleted successfully";
        Response response = new Response(deckResponse, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/decks")
    public ResponseEntity<Response> updateDeck(@RequestBody UpdateDeck updateDeck) throws Exception {
        deckServiceV2.updateDeckV2(updateDeck);
        Response response = new Response(true, "Edit card set was successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/decks/{id}")
    public ResponseEntity<Response> getDeckWithId(@PathVariable Long id) {
        DetailDeckResponse deckDto = deckService.getDeckWithId(id);
        String message = "Query successful";
        Response response = new Response(deckDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/decks/{id}/join-cards")
    public ResponseEntity<?> getJoinCards(@PathVariable long id, @RequestParam boolean isOnlyFavorite) {
        List<JoinCardElement> joinCardElements = deckServiceV2.getJoinCards(id, isOnlyFavorite);
        Response response = new Response(joinCardElements, "Query successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/decks/{id}/study-cards")
    public ResponseEntity<Response> getStudyCards(@PathVariable long id,
            @RequestParam(required = false) boolean isFavorite) {
        List<CardResponse> cardsDto = deckServiceV2.getStudyCards(id, isFavorite);
        String message = "Query successful";
        Response response = new Response(cardsDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/decks/{id}/question-cards")
    public ResponseEntity<?> getQuestionCard(
            @PathVariable long id,
            @RequestParam int numberOfQuestions,
            @RequestParam String optionType,
            @RequestParam boolean isOnlyFavorite) {
        List<Question> questions = deckServiceV2.generateQuiz(id, numberOfQuestions, optionType, isOnlyFavorite);
        Response response = new Response(questions, "Query successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
