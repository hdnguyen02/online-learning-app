package com.hdnguyen.dto.deck;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeckRequest {
    private String name;
    private String description;
    private Boolean isPublic;
}