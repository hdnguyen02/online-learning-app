package com.hdnguyen.dto.deck;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.entity.Deck;
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
    private Integer numberCards;
    @JsonFormat(timezone = "yyyy-MM-dd")
    private Date createAt;
    private Boolean isPublic;
    private Integer quantityClone;
    private UserResponse user; // thông tin của chủ thẻ

    public DeckResponse(Deck deck) {
        this.id = deck.getId();
        this.name = deck.getName();
        this.description = deck.getDescription();
        this.numberCards = deck.getCards() == null ? 0 : deck.getCards().size();
        this.createAt = deck.getCreateAt();
        this.isPublic = deck.getIsPublic();
        this.quantityClone = deck.getQuantityClone();
        this.user = new UserResponse(deck.getUser());

    }
}