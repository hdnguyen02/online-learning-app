package com.hdnguyen.controller;

import com.hdnguyen.dto.card.CardResponse;
import com.hdnguyen.dto.Response;
import com.hdnguyen.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("${system.version}")
public class CardController {
    private final CardService cardService;
    @PostMapping("cards")
    public ResponseEntity<Response> createCard(
            @RequestParam Long idDeck,
            @RequestParam String term,
            @RequestParam String definition,
            @RequestParam (required = false) String example,
            @RequestParam (required = false) MultipartFile image,
            @RequestParam (required = false) MultipartFile audio) throws Exception {

        CardResponse cardDto = cardService.createCard(idDeck, term, definition, example, image, audio);
        String message = "Tạo thẻ thành công";
        Response response = new Response(cardDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/cards/search")
    public ResponseEntity<Response> searchCards(@RequestParam String content) {
        List<CardResponse> cardsDto = cardService.searchCards(content);
        String message = "Truy vấn card thành công";
        Response response = new Response(cardsDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/cards/filter")
    public ResponseEntity<Response> filterCards(@RequestParam (required = false) Long idDeck,
                                                @RequestParam (required = false) Boolean isFavourite,
                                                @RequestParam (required = false) Boolean isRemembered) {
        List<CardResponse> cardsDto = cardService.filterCards(idDeck, isFavourite, isRemembered);
        String message = "Truy vấn card thành công";
        Response response = new Response(cardsDto, message, true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/cards")
    public ResponseEntity<Response> getCards() {
        List<CardResponse> cardsDto = cardService.getCards(); // thực hiện truy vấn tất cả.
        String message = "Truy vấn card thành công";
        Response response = new Response(cardsDto, message, true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("cards/{id}")
    public ResponseEntity<Response> getCardWithId(@PathVariable Long id) throws Exception {
        CardResponse cardDto = cardService.getCardWithId(id);
        String message = "Truy vấn thẻ thành công";
        Response response = new Response(cardDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("cards")
    public ResponseEntity<Response> deleteCard(@RequestParam long [] ids) {
        String message = "Xóa thẻ thành công";
        cardService.deleteCards(ids);
        Response response = new Response(null, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("cards/{id}")
    public ResponseEntity<Response> deleteCard(@PathVariable Long id) throws Exception {
        CardResponse cardDto = cardService.deleteCard(id);
        String message = "Xoá thẻ thành công";
        Response response = new Response(cardDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("cards/{id}")
    public ResponseEntity<Response> updateCard(@PathVariable Long id,
                                               @RequestParam (required = false) Long idDeck,
                                               @RequestParam (required = false) String term,
                                               @RequestParam (required = false) String definition,
                                               @RequestParam (required = false) String example,
                                               @RequestParam (required = false) MultipartFile image,
                                               @RequestParam (required = false) MultipartFile audio,
                                               @RequestParam (required = false) Boolean isFavourite,
                                               @RequestParam (required = false) Boolean isRemembered) throws Exception {
        CardResponse cardDto = cardService.updateCard(id ,idDeck, term, definition,
                example,image,audio,isFavourite, isRemembered);
        String message = "Hiệu chỉnh thẻ thành công";
        Response response = new Response(cardDto, message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
