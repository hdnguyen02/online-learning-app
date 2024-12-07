package com.online_learning.dto.group;

import com.online_learning.dto.auth.UserResponse;
import com.online_learning.entity.Group;
import com.online_learning.entity.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupResponse {
    private Long id;
    private UserResponse owner;
    private String name;
    private String description;
    private Integer quantityMembers;
    private Integer quantityComments;
    private Integer quantityCommonDecks;
    private Boolean isPublic;
    private Date createdDate;
    List<UserGroupResponse> userGroups = new ArrayList<>();


    public static GroupResponse mapToGroupDto(Group group){
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(group.getId());
        groupResponse.setName(group.getName());
        groupResponse.setOwner(new UserResponse(group.getOwner()));
        groupResponse.setDescription(group.getDescription());
        groupResponse.setIsPublic(group.getIsPublic());
        groupResponse.setQuantityMembers((int)group.getUserGroups().stream().filter(UserGroup::isActive).count());
        groupResponse.setCreatedDate(group.getCreatedDate());
        // assignment

        // comments
        int quantityComments = group.getComments() != null ? group.getComments().size() : 0;
        groupResponse.setQuantityComments(quantityComments);

        // commonDecks
        int quantityCommonDecks = group.getCommonDecks() != null ? group.getCommonDecks().size() : 0;

        groupResponse.setQuantityCommonDecks(quantityCommonDecks);




        return groupResponse;
    }

    public static GroupResponse mapToGroupDtoDetail(Group group){
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(group.getId());
        groupResponse.setName(group.getName());
        groupResponse.setOwner(new UserResponse(group.getOwner()));
        groupResponse.setDescription(group.getDescription());
        groupResponse.setIsPublic(group.getIsPublic());

        List<UserGroup> userGroupsActive = group.getUserGroups().stream().filter(UserGroup::isActive).toList();

        List<UserGroupResponse> userResponses = new ArrayList<>();
        userGroupsActive.forEach(ele->{
            userResponses.add(new UserGroupResponse(ele));
        });

        // comments
        int quantityComments = group.getComments() != null ? group.getComments().size() : 0;
        groupResponse.setQuantityComments(quantityComments);

        // commonDecks
        int quantityCommonDecks = group.getCommonDecks() != null ? group.getCommonDecks().size() : 0;

        groupResponse.setQuantityCommonDecks(quantityCommonDecks);


        groupResponse.setUserGroups(userResponses);

        groupResponse.setQuantityMembers(userGroupsActive.size());



        return groupResponse;
    }

}
