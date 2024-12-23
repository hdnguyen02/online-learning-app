package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Table(name = "common_decks")
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

    @OneToMany(mappedBy = "commonDeck", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CommonCard> cards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_group", nullable = false)
    private Group group;

    // thêm vào thuộc tính được tạo bởi bởi ai.
    // private String configLanguage; // lưu lại config.
    // thay đổi language
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_language")
    private Language language;
}
