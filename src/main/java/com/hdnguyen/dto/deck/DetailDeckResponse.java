package com.hdnguyen.dto.deck;

import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.dto.card.CardOfDeck;
import com.hdnguyen.entity.Deck;
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
    private Integer quantityClone;
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
        this.quantityClone = deck.getQuantityClone();
        this.user = new UserResponse(deck.getUser());
        this.quantityClone = this.cards.size();
    }
}