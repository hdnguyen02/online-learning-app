package com.online_learning.entity;

import com.online_learning.core.BaseEntity;
import com.online_learning.core.Gender;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Table(name = "users", indexes = { @Index(name = "idx_email", columnList = "email") })
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    private Date dateOfBirth;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 20)
    private String phone;

    private String avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Deck> decks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "name_role"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserGroup> userGroups;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Invoice> invoices;

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public User(String email) {
        this.email = email;
    }

    public User(Long id) {
        this.setId(id);
    }
}
