package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
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
public class Comment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Comment comment;

    @Column(length = Integer.MAX_VALUE)
    private String content;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Comment(Long id) {
        super((id));
    }
}
