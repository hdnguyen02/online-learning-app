package com.online_learning.dto.auth;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isRemember;
    private String contentOtp;
}
