package com.hdnguyen.dto.card;

import com.hdnguyen.dto.card.DeckOfCard;
import com.hdnguyen.entity.Card;
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
    private Date createAt;
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
        this.createAt = card.getCreateAt();
        this.isFavourite = card.getIsFavourite();
        this.isRemembered = card.getIsRemembered();

        if (card.getDeck() != null) {
            this.deck = new DeckOfCard(card.getDeck()); // không cần thông tin này.
        }


    }
}