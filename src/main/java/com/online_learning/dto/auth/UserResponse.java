package com.online_learning.dto.auth;

import com.online_learning.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Date createAt;
    private Boolean isEnabled;
    private List<String> roles;
    private String avatar;
    private String gender;
    private String phone;
    private Integer age;
    

    public UserResponse(User user) {

        roles = new ArrayList<>();
        avatar = user.getAvatar();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        dateOfBirth = user.getDateOfBirth();
        createAt = user.getCreateAt();
        isEnabled = user.getIsEnabled();
        gender = user.getGender();
        phone = user.getPhone();

        user.getRoles().forEach(role -> {
            roles.add(role.getName());
        });
    }
}
    