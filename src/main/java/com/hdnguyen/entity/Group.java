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

    @OneToMany(mappedBy = "group")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<UserGroup> userGroups;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private List<Assignment> assignments;

    private Boolean isPublic; // lớp học có được public hay không.

    public Group(Long id) {
        this.id = id;
    }
}
