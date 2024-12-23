package com.online_learning.dto.groupv2;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.online_learning.entity.CommonDeck;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicCommonDeckResponseV2 {
    private long id;
    private String name;
    private String description;
    private Integer quantityCards;
    private Date createdDate;

    public PublicCommonDeckResponseV2(CommonDeck commonDeck) {
        this.id = commonDeck.getId();
        this.name = commonDeck.getName();
        this.description = commonDeck.getDescription();
        this.quantityCards = Optional.ofNullable(commonDeck.getCards()).map(List::size).orElse(0);
    }
}
