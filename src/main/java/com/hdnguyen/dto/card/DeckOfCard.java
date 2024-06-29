package com.hdnguyen.dto.card;

import com.hdnguyen.entity.Deck;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeckOfCard {
    private Long id;
    private String name;

    public DeckOfCard(Deck deck) {
        this.id = deck.getId();
        this.name = deck.getName();
    }
}