package com.online_learning.controller;


import com.online_learning.dto.deckv2.CreateDeck;
import com.online_learning.service.DeckServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RequestMapping("api/v2")
@RequiredArgsConstructor
public class DeckControllerV2 {

    private final DeckServiceV2 deckServiceV2;

    public ResponseEntity<Boolean> createDeckV2(@RequestBody CreateDeck createDeck) {
        deckServiceV2.createDeckV2(createDeck);
        return ResponseEntity.ok(true);
    }


}
