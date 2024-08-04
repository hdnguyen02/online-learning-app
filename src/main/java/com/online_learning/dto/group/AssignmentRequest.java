package com.online_learning.dto.group;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequest {
    private Long idGroup;
    private MultipartFile file; // lấy file
    private String name;
    private String description;
    private String deadline;
}
