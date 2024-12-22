package com.online_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageResponse {
    private long id;
    private String code;
    private String nameInternational;
    private String nameVietnamese;
}