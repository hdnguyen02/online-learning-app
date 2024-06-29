package com.hdnguyen.dao;

import com.hdnguyen.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, String> {
}
