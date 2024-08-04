package com.online_learning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "comments")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "parentId")
    private Comment comment;

    @Column(length = Integer.MAX_VALUE)
    private String content;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "email_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public  Comment(Long id) {
        this.id = id;
    }
}
