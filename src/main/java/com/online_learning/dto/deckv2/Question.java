package com.online_learning.dto.deckv2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String questionContent; // Nội dung câu hỏi
    private List<String> answers;  // Danh sách đáp án (bao gồm đúng và sai)
    private String correctAnswer;  // Đáp án đúng
}