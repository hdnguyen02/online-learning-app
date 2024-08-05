package com.online_learning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Table(name="decks")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="email_user")
    private User user;

    @OneToMany(mappedBy = "deck",fetch = FetchType.EAGER)
    private List<Card> cards;

    private Date createAt;

    private Boolean isPublic;

    private Integer quantityClones;

}
