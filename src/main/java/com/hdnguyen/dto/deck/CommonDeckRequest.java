package com.hdnguyen.dto.deck;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class CommonDeckRequest {
    private Long idGroup; // khởi tạo bao gồm id group vào => biết được nó thuộc thằng nào.
    private String name;
    private String description;
}
