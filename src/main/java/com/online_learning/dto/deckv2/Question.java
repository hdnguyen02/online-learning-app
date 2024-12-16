package com.online_learning.dto.deckv2;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private String id;
    private String questionContent; // Nội dung câu hỏi
//    private List<String> answers;  // Danh sách đáp án (bao gồm đúng và sai)
    private List<Answer> answers;
    private Answer correctAnswer;  // Đáp án đúng
    private String type;
    private String audio;
    private String image;
}