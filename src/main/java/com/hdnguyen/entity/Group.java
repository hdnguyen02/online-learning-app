package com.hdnguyen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "grades")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Group extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<CommonDeck> commonDecks;

    private Boolean isPublic;

    public Group(Long id) {
        this.id = id;
    }
}
