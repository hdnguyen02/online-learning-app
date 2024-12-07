package com.online_learning.dto.deckv2;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinCardRequest {
    private Long id;
    private Boolean isOnlyFavorite;
}
