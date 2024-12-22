package com.online_learning.entity;

import java.util.List;

import com.online_learning.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "Languages")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Language extends BaseEntity {
    private String code;
    private String nameInternational;
    private String nameVietnamese;

    @OneToMany(mappedBy = "language", fetch = FetchType.EAGER)
    private List<Deck> decks;

    @OneToMany(mappedBy = "language", fetch = FetchType.EAGER)
    private List<CommonDeck> commonDecks;

    // Đếm số lượng sử dụng
    public int countDecks() {
        int deckCount = (decks != null) ? decks.size() : 0;
        int commonDeckCount = (commonDecks != null) ? commonDecks.size() : 0;
        return deckCount + commonDeckCount;
    }

}
