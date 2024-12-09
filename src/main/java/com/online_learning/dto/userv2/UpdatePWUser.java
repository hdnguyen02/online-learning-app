package com.online_learning.dto.userv2;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePWUser {
    private String oldPW;
    private String newPW;
    private String confirmPW;
}
