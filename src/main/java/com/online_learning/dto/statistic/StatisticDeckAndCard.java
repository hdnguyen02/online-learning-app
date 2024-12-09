package com.online_learning.dto.statistic;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticDeckAndCard {
    private String month;
    private int numberDecks;
    private int numberCards;
}
