package com.online_learning.dto.common_deckv2;


import com.online_learning.entity.CommonCard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryCommonCard {
    private Long id; // có thể null
    private String term;
    private String definition;
    private String example;
    private String image;
    private String audio;

    public QueryCommonCard(CommonCard commonCard) {
        this.id = commonCard.getId();
        this.term = commonCard.getTerm();
        this.definition = commonCard.getDefinition();
        this.example = commonCard.getExample();
        this.image = commonCard.getImage();
        this.audio = commonCard.getAudio();
    }
}
