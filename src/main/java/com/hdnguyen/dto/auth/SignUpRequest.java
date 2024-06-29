package com.hdnguyen.dto.auth;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private String firstName; // đảm bảo thông tin cần điền
    private String lastName; // đảm bảo thông tin cần điền
    private Boolean isRemember;
}
