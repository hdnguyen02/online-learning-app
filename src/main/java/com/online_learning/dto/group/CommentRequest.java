package com.online_learning.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private Long id;
    private String email;
    private String content;
    private Long parentId;
    private Long groupId;
}

