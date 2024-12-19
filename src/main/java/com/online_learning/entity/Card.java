package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name="cards")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card extends BaseEntity {
    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    private String definition;

    private String image;

    private String audio;

    private String example;

    private Boolean isFavourite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_deck")
    private Deck deck;


    @PrePersist
    public void  prePersist() {
        super.prePersist();
        this.isFavourite = false;
    }
}