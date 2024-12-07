package com.online_learning.dto.common_deckv2;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommonCard {
    private Long id; // có thể null
    private String term;
    private String definition;
    private String example;
    private String image;
    private String audio;
}
