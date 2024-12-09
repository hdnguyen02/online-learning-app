package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "grades")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Group extends BaseEntity {
    private String name;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    @ManyToOne
    @JoinColumn(name = "email_owner")
    private User owner;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<CommonDeck> commonDecks;

    private Boolean isPublic;


    public Group(Long id) {
        super(id);
    }
}
