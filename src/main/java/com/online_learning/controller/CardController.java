package com.online_learning.controller;

import com.online_learning.dto.card.CardResponse;
import com.online_learning.dto.Response;
import com.online_learning.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class CardController {
    private final CardService cardService;

    @GetMapping("/cards/search")
    public ResponseEntity<Response> searchCards(@RequestParam String content) {
        List<CardResponse> cardsDto = cardService.searchCards(content);
        String message = "Query successful";
        Response response = new Response(cardsDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/cards")
    public ResponseEntity<Response> getCards() {
        List<CardResponse> cardsDto = cardService.getCards(); // thực hiện truy vấn tất cả.
        String message = "Query successful";
        Response response = new Response(cardsDto, message, true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/cards/{id}/favourite")
    public ResponseEntity<?> updateFavourite(@PathVariable long id, @RequestParam boolean value) {
        cardService.updateFavourite(id, value);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/cards")
    public ResponseEntity<Response> deleteCard(@RequestParam long[] ids) {

        String message = "Deleted successfully";
        cardService.deleteCards(ids);
        Response response = new Response(null, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
