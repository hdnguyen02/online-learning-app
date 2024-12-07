package com.online_learning.dto.deckv2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCard {
    private String term;
    private String definition;
    private String example;
    private String image;
    private String audio;
}

