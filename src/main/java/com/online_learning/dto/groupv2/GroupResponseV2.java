package com.online_learning.dto.groupv2;

import com.online_learning.entity.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponseV2 {

    // cái này không đi quá sâu vào group => chỉ đưa ra 1 vài trường thôi
    private long id;
    private String name;
    private String description;
    private String emailOwner; // thông tin owner.

    private int memberCount;
    private int commonDeckCount;

    public GroupResponseV2(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.memberCount = (group.getUserGroups() != null) ? group.getUserGroups().size() : 0;
        this.commonDeckCount = (group.getCommonDecks() != null) ? group.getCommonDecks().size() : 0;
        this.emailOwner = group.getOwner().getEmail();
    }
}
