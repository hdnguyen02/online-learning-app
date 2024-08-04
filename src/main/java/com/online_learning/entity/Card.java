package com.online_learning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name="cards")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String term;
    @Column(nullable = false)
    private String definition;
    private String image;
    private String audio;
    private String example;
    private Date createAt;

    @Column(name = "is_favourite")
    private Boolean isFavourite;

    @Column(name = "is_remembered")
    private Boolean isRemembered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_deck")
    private Deck deck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_common_deck")
    private CommonDeck commonDeck;

    @PrePersist
    public void  prePersist() {
        this.createAt = new Date();
        this.isFavourite = false;
        this.isRemembered = false;
    }

}