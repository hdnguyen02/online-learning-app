package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_group")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGroup extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Group group;

    private String tokenActive;

    private boolean isActive;
    private String approachType; // SEND_MAIL, SUBMIT
}
