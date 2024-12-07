package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
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
public class CommonDeck extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    @OneToMany(mappedBy = "commonDeck",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CommonCard> cards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_group", nullable = false)
    private Group group;

    private String configLanguage; // lưu lại config.
}
