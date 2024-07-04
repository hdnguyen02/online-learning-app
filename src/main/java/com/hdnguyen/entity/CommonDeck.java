package com.hdnguyen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Table(name="common_decks")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonDeck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    @OneToMany(mappedBy = "commonDeck",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Card> cards;

    private Date createAt;

    private Integer quantityClone; // số lần bộ thẻ này được clone ra

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_group", nullable = false)
    private Group group;

}
