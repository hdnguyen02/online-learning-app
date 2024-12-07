package com.online_learning.dto.auth;

import com.online_learning.core.Gender;
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
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date createdDate;
    private Boolean isEnabled;
    private List<String> roles;
    private String avatar;
    private Gender gender;
    private String phone;
    

    public UserResponse(User user) {

        roles = new ArrayList<>();
        id = user.getId();
        avatar = user.getAvatar();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        dateOfBirth = user.getDateOfBirth();
        isEnabled = user.getIsEnabled();
        gender = user.getGender();
        phone = user.getPhone();

        createdDate = user.getCreatedDate();
        user.getRoles().forEach(role -> {
            roles.add(role.getName());
        });
    }
}
    