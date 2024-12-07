package com.online_learning.dto.deck;

import com.online_learning.dto.auth.UserResponse;
import com.online_learning.entity.Deck;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeckResponse {
    private Long id;
    private String name;
    private String description;
    private Integer quantityCards;
    private Date createdDate;
    private Boolean isPublic;
    private Integer quantityClones;
    private UserResponse user;

    public DeckResponse(Deck deck) {
        this.id = deck.getId();
        this.name = deck.getName();
        this.description = deck.getDescription();
        this.quantityCards = deck.getCards() == null ? 0 : deck.getCards().size();

        this.isPublic = deck.getIsPublic();
        this.quantityClones = deck.getQuantityClones();
        this.user = new UserResponse(deck.getUser());
        this.createdDate = deck.getCreatedDate();

    }
}