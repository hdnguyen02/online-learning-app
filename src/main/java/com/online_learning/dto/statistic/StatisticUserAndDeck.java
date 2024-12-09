package com.online_learning.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticUserAndDeck {
    private String month;
    private int numberUsers;
    private int numberDecks;
}
