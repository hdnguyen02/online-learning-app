package com.online_learning.dto.card;

import com.online_learning.entity.Card;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardResponse {
    private Long id;
    private String term;
    private String definition;
    private String image;
    private String audio;
    private String example;
    private Date createdDate;
    private Boolean isFavourite;
    private Boolean isRemembered;
    private DeckOfCard deck;

    public CardResponse(Card card){
        this.id = card.getId();
        this.term = card.getTerm();
        this.definition = card.getDefinition();
        this.image = card.getImage();
        this.audio = card.getAudio();
        this.example = card.getExample();

        this.isFavourite = card.getIsFavourite();
        this.createdDate = card.getCreatedDate();

        if (card.getDeck() != null) {
            this.deck = new DeckOfCard(card.getDeck()); // không cần thông tin này.
        }


    }
}