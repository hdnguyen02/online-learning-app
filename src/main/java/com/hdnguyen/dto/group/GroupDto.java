package com.hdnguyen.dto.group;

import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.entity.Group;
import com.hdnguyen.entity.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupDto {
    private Long id;
    private UserResponse owner;
    private String name;
    private String description;
    private int quantity;
    List<UserResponse> userGroups = new ArrayList<>();

    public static GroupDto mapToGroupDto(Group group){
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setOwner(new UserResponse(group.getOwner()));
        groupDto.setDescription(group.getDescription());

        int sizeUserGroups = (int)group.getUserGroups().stream().filter(UserGroup::isActive).count();
        groupDto.setQuantity(sizeUserGroups);

        return  groupDto;
    }

    public static GroupDto mapToGroupDtoDetail(Group group){
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setOwner(new UserResponse(group.getOwner()));
        groupDto.setDescription(group.getDescription());

        List<UserGroup> userGroupsActive = group.getUserGroups().stream().filter(UserGroup::isActive).toList();
        List<UserResponse> userResponses = new ArrayList<>();
        userGroupsActive.forEach(ele->{
            userResponses.add(new UserResponse(ele.getUser()));
        });

        groupDto.setUserGroups(userResponses);

        int sizeUserGroups = userGroupsActive.size();

        groupDto.setQuantity(sizeUserGroups);

        return  groupDto;
    }

}
