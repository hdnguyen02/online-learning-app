package com.hdnguyen.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_group")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "email_user")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Group group;

    private String tokenActive;

    private boolean isActive;
    private String approachType; // SEND_MAIL, SUBMIT
}

