package com.online_learning.dto.deck;

import com.online_learning.dto.auth.UserResponse;
import com.online_learning.dto.card.CardOfDeck;
import com.online_learning.entity.Deck;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DetailDeckResponse {
    private Long id;
    private String description;
    private String name;
    private Date createAt;
    private List<CardOfDeck> cards;
    private Boolean isPublic;
    private Integer quantityClones;
    private UserResponse user;
    private Integer quantityCards;

    public DetailDeckResponse(Deck deck) {
        cards = new ArrayList<>();
        this.id = deck.getId();
        this.description = deck.getDescription();
        this.name = deck.getName();
        this.createAt = deck.getCreateAt();
        deck.getCards().forEach(card -> {
            this.cards.add(new CardOfDeck(card));
        });
        this.isPublic = deck.getIsPublic();
        this.quantityClones = deck.getQuantityClone();
        this.user = new UserResponse(deck.getUser());
        this.quantityCards = this.cards.size();
    }
}