package com.online_learning.dto.groupv2;

import java.util.List;

import com.online_learning.entity.Group;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverviewGroupsResponseV2 {
    public List<GroupResponseV2> ownerGroups; // có thể có 2 trường hợp =>
    public List<GroupResponseV2> groupsAttendance;

    public OverviewGroupsResponseV2(List<Group> ownerGroups, List<Group> groupsAttendance) {
        this.ownerGroups = ownerGroups.stream().map(GroupResponseV2::new).toList();
        this.groupsAttendance = groupsAttendance.stream().map(GroupResponseV2::new).toList();
    }
}
