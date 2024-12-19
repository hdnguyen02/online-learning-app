package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "decks")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Deck extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "deck", fetch = FetchType.EAGER)
    private List<Card> cards;

    private Boolean isPublic;

    private Integer quantityClones;

    private String configLanguage; // lưu lại config.
}
