package com.online_learning.dto.deck;

import com.online_learning.dto.group.GroupResponse;
import com.online_learning.entity.CommonDeck;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommonDeckResponse {
    private Long id;
    private String name;
    private String description;
    private Integer quantityCards;
    private Date createdDate;
    private GroupResponse group;

    public CommonDeckResponse(CommonDeck commonDeck) {
        this.id = commonDeck.getId();
        this.name = commonDeck.getName();
        this.description = commonDeck.getDescription();
        this.quantityCards = commonDeck.getCards() == null ? 0 : commonDeck.getCards().size();
        this.createdDate = commonDeck.getCreatedDate();

        this.group = GroupResponse.mapToGroupDto(commonDeck.getGroup());
    }
}
