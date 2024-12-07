package com.online_learning.dto.group;

import com.online_learning.dto.auth.UserResponse;
import com.online_learning.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponse {
    private Long id;
    private String content;
    private UserResponse user;
    private List<CommentResponse> commentsChild = new ArrayList<>();
    private Date createdDate;

    public static CommentResponse mapToCommentDto(Comment comment){
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setContent(comment.getContent());
        commentResponse.setUser(new UserResponse(comment.getUser()));
        commentResponse.setCreatedDate(comment.getCreatedDate());


        List<CommentResponse> commentResponses = new ArrayList<>();
        comment.getComments().forEach(commentDB -> {
            commentResponses.add(CommentResponse.mapToCommentDto(commentDB)   );
        });
        commentResponse.setCommentsChild(commentResponses);

        return commentResponse;
    }
}
