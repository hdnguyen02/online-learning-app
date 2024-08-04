package com.online_learning.dao;

import com.online_learning.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, String> {
}
