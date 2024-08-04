package com.online_learning.dto.card;

import com.online_learning.entity.Deck;
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