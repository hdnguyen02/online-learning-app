package com.online_learning.dto.deckv2;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinCardElement {
    private String id; // id giả.
    private String content; // có thể là thuật ngữ hoặc định nghĩa
    private String key;
}
