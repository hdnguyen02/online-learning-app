package com.online_learning.dto.deckv2;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JoinCardResponse {
    private int numberCard;
    List<JoinCardTerm> joinCardTerms;
    List<JoinCardDefinition> joinCardDefinitions;
}
