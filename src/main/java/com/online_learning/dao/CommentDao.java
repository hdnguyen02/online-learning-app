package com.online_learning.dao;

import com.online_learning.entity.Comment;
import com.online_learning.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findByCommentIsNullAndGroup(Group group);
}
