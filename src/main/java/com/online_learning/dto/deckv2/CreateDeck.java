package com.online_learning.dto.deckv2;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateDeck {

    private String name;
    private String description;
    private Boolean isPublic;
    private String configLanguage;
    private List<CreateCard> cards;
}

