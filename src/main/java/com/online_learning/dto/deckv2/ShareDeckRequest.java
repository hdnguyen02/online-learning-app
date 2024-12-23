package com.online_learning.dto.deckv2;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareDeckRequest {

    // nhận vào danh sách id group + id của bộ thẻ.

    private List<Long> idGroups;
    private long idDeck;

}
