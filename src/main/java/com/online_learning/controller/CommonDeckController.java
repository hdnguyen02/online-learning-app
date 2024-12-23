package com.online_learning.controller;

import com.online_learning.dto.Response;
import com.online_learning.dto.common_deckv2.UpdateCommonDeck;
import com.online_learning.dto.deck.CommonDeckRequest;
import com.online_learning.dto.deckv2.JoinCardElement;
import com.online_learning.dto.deckv2.Question;
import com.online_learning.service.CommonDeckService;
import com.online_learning.service.CommonDeckServiceV2;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    @GetMapping("/common-decks/{id}/question-cards")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getQuestionCard(
            @PathVariable long id,
            @RequestParam int numberOfQuestions,
            @RequestParam String optionType) throws Exception {
        List<Question> questions = commonDeckServiceV2.generateQuiz(id, numberOfQuestions, optionType);
        Response response = new Response(questions, "Query successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/common-decks/{id}/join-cards")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getJoinCards(@PathVariable long id, @RequestParam int quantity) throws Exception {
        List<JoinCardElement> joinCardElements = commonDeckServiceV2.getJoinCommonCards(id, quantity);
        Response response = new Response(joinCardElements, "Query successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/common-decks")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> createCommonDeck(@RequestBody CommonDeckRequest commonDeckRequest) throws Exception {

        boolean result = commonDeckService.createCommonDeck(commonDeckRequest);
        Response response = Response.builder()
                .message("Created successfully")
                .data(result)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/common-decks")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getCommonDecks(@RequestParam Long idGroup) {

        Response response = Response.builder()
                .message("Query successful")
                .data(commonDeckService.getCommonDecksByIdGroup(idGroup))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/common-decks/{id}")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> getCommonDeck(@PathVariable long id) {

        Response response = Response.builder()
                .message("Query successful")
                .data(commonDeckService.getCommonDeck(id))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/common-decks/{id}")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> editCommonDeck(@PathVariable Long id,
            @RequestBody CommonDeckRequest commonDeckRequest) {

        Response response = Response.builder()
                .message("Edited successfully")
                .data(commonDeckService.editCommonDeck(id, commonDeckRequest))
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/common-decks")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> updateCommonDeckV2(@RequestBody UpdateCommonDeck updateCommonDeck) throws Exception {
        commonDeckServiceV2.updateCommonDeckV2(updateCommonDeck);
        Response response = new Response(true, "Edit common card set was successful", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/common-decks/{id}")
    @PreAuthorize("hasRole('GROUP_ACTIVITIES_ACCESS')")
    public ResponseEntity<?> deleteCommonDeck(@PathVariable Long id) {

        Response response = Response.builder()
                .message("Deleted successfully")
                .data(commonDeckService.deleteCommonDeck(id))
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
