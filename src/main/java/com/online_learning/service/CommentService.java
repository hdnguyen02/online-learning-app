package com.online_learning.service;

import com.online_learning.util.Helper;
import com.online_learning.dao.CommentDao;
import com.online_learning.dto.group.CommentResponse;
import com.online_learning.entity.Comment;
import com.online_learning.entity.Group;
import com.online_learning.entity.User;
import com.online_learning.dto.group.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentRepository;

    @Autowired
    private Helper helper;

    public boolean createComment(CommentRequest commentRequest) {

        String emailUser = helper.getEmailUser();

        Comment comment = new Comment();

        Date created = new Date();
        comment.setCreated(created);
        comment.setUser(new User(emailUser));

        comment.setContent(commentRequest.getContent());
        if (commentRequest.getParentId() != null) {
            comment.setComment(new Comment(commentRequest.getParentId()));
        }
        comment.setGroup(new Group(commentRequest.getGroupId()));

        commentRepository.save(comment);

        return true;
    }

    public boolean deleteComment(Long id) {
        return false;
    }

    public List<CommentResponse> getListComment() {
        return null;
    }

    public List<CommentResponse> getCommentByGroupId(Long id) {
        List<Comment> comments = commentRepository.findByCommentIsNullAndGroup(new Group(id));
        List<CommentResponse> commentResponses = new ArrayList<>();

        comments.forEach(comment -> {
            commentResponses.add(CommentResponse.mapToCommentDto(comment));
        });
        return commentResponses;
    }
}
