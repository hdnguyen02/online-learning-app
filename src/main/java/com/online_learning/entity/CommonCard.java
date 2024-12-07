package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name="common_cards")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonCard extends BaseEntity {
    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    private String definition;

    private String image;

    private String audio;

    private String example;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_common_deck")
    private CommonDeck commonDeck;
}