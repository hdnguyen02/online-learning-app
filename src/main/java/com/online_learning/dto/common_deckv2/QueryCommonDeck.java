package com.online_learning.dto.common_deckv2;

import com.online_learning.dto.group.GroupResponse;
import com.online_learning.entity.CommonDeck;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class QueryCommonDeck {
    private Long id;
    private String description;
    private String name;
    private String configLanguage;
    private Date createdDate;
    private List<QueryCommonCard> cards;
    private GroupResponse group;

    public QueryCommonDeck(CommonDeck commonDeck) {
        this.cards = new ArrayList<>();
        this.id = commonDeck.getId();
        this.description = commonDeck.getDescription();
        this.name = commonDeck.getName();
        this.configLanguage = commonDeck.getLanguage().getCode();
        this.createdDate = commonDeck.getCreatedDate();
        commonDeck.getCards().forEach(card -> {
            this.cards.add(new QueryCommonCard(card));
        });
        this.group = GroupResponse.mapToGroupDto(commonDeck.getGroup());
    }
}
