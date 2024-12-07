package com.online_learning.dto.common_deckv2;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateCommonDeck {
    private Long id; // id deck cần chỉnh sữa
    private String name;
    private String description;
    private String configLanguage;
    private List<UpdateCommonCard> cards;
}
