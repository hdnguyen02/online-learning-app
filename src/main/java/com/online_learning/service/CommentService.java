package com.online_learning.service;

import com.online_learning.dao.GroupDao;
import com.online_learning.dto.group.GroupResponse;
import com.online_learning.util.Helper;
import com.online_learning.dao.CommentDao;
import com.online_learning.dto.group.CommentResponse;
import com.online_learning.entity.Comment;
import com.online_learning.entity.Group;
import com.online_learning.entity.User;
import com.online_learning.dto.group.CommentRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    private final GroupDao groupDao;



    @Autowired
    private Helper helper;

    public boolean createComment(CommentRequest commentRequest) {


        Optional<Group> groupOptional = groupDao.findById(commentRequest.getGroupId());
        if (groupOptional.isEmpty()) throw new EntityNotFoundException("Not found group with id: " + commentRequest.getGroupId());

        Group group = groupOptional.get();

        User user = helper.getUser();

        Comment comment = new Comment();

        comment.setUser(user);

        comment.setContent(commentRequest.getContent());
        if (commentRequest.getParentId() != null) {

            Comment commentParent = commentDao.findById(commentRequest.getParentId()).orElseThrow();
            comment.setComment(commentParent);
        }
        comment.setGroup(group);

        commentDao.save(comment);

        return true;
    }

    public boolean deleteComment(Long id) {
        return false;
    }

    public List<CommentResponse> getListComment() {
        return null;
    }

    public List<CommentResponse> getCommentByGroupId(Long id) {
        List<Comment> comments = commentDao.findByCommentIsNullAndGroup(new Group(id));
        List<CommentResponse> commentResponses = new ArrayList<>();

        comments.forEach(comment -> {
            commentResponses.add(CommentResponse.mapToCommentDto(comment));
        });
        return commentResponses;
    }
}
