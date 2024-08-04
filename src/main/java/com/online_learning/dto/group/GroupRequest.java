package com.online_learning.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupRequest {

    private String name;
    private String description;
    private Boolean isPublic;
}
