package com.hdnguyen.dto.group;

import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.entity.Comment;
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
    private Date created;

    public static CommentResponse mapToCommentDto(Comment comment){
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setContent(comment.getContent());
        commentResponse.setUser(new UserResponse(comment.getUser()));
        commentResponse.setCreated(comment.getCreated());

        List<CommentResponse> commentResponses = new ArrayList<>();
        comment.getComments().forEach(commentDB -> {
            commentResponses.add(CommentResponse.mapToCommentDto(commentDB)   );
        });
        commentResponse.setCommentsChild(commentResponses);

        return commentResponse;
    }
}
