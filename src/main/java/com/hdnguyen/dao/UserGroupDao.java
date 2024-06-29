package com.hdnguyen.dao;

import com.hdnguyen.entity.Group;
import com.hdnguyen.entity.User;
import com.hdnguyen.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupDao extends JpaRepository<UserGroup, Long> {
    List<UserGroup> findByUserAndGroup(User user, Group group);

    Optional<UserGroup> findByGroupAndTokenActive(Group group, String token);

    List<UserGroup> findByUserAndIsActive(User user, boolean isActive);
}
