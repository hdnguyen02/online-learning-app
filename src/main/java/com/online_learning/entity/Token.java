package com.online_learning.entity;


import com.online_learning.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name="tokens")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Boolean isSignOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="email_user")
    private User user;
}
