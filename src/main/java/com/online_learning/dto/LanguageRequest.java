package com.online_learning.dto;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageRequest {
    private String code;
    private String nameInternational;
    private String nameVietnamese;
}
