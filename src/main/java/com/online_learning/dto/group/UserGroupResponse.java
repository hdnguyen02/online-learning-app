package com.online_learning.dto.group;

import com.online_learning.entity.UserGroup;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserGroupResponse {
    private Long id; // idUserGroup
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date createdDate;
    private Boolean isEnabled;
    private List<String> roles;
    private String avatar;
    private String gender;
    private String phone;

    public UserGroupResponse(UserGroup userGroup) {

        id = userGroup.getId();

        roles = new ArrayList<>();
        avatar = userGroup.getUser().getAvatar();
        email = userGroup.getUser().getEmail();
        firstName = userGroup.getUser().getFirstName();
        lastName = userGroup.getUser().getLastName();
        dateOfBirth = userGroup.getUser().getDateOfBirth();
        createdDate = userGroup.getCreatedDate();

        isEnabled = userGroup.getUser().getIsEnabled();

        phone = userGroup.getUser().getPhone();

        userGroup.getUser().getRoles().forEach(role -> {
            roles.add(role.getName());
        });
    }
}
