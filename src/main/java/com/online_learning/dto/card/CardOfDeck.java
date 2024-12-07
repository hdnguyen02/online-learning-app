package com.online_learning.dto.card;

import com.online_learning.entity.Card;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardOfDeck {
    private Long id;
    private String term;
    private String definition;
    private String image;
    private String audio;
    private String example;
    private Date createAt;
    private Boolean isFavourite;

    public CardOfDeck(Card card){
        this.id = card.getId();
        this.term = card.getTerm();
        this.definition = card.getDefinition();
        this.image = card.getImage();
        this.audio = card.getAudio();
        this.example = card.getExample();

        this.isFavourite = card.getIsFavourite();
    }
}