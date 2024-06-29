package com.hdnguyen.dto.group;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitRequest {
    private Long idGroup;
    private Long idAssignment;
}
