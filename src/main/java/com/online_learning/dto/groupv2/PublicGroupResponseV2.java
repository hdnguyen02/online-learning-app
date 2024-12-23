package com.online_learning.dto.groupv2;

import java.util.List;

import com.online_learning.entity.Group;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicGroupResponseV2 {
    public List<GroupResponseV2> groupsJoined; // có thể có 2 trường hợp =>
    public List<GroupResponseV2> groups;

    public PublicGroupResponseV2(List<Group> groupsJoined, List<Group> groups) {
        this.groupsJoined = groupsJoined.stream().map(GroupResponseV2::new).toList();
        this.groups = groups.stream().map(GroupResponseV2::new).toList();
    }
}
