package com.online_learning.dto.userv2;


import com.online_learning.core.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UpdateUser {
    private Date dateOfBirth;
    private String firstName;
    private String lastName;
    private String phone;
    private Gender gender;
}
