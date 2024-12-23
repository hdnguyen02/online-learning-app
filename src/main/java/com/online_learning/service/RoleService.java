package com.online_learning.service;

import com.online_learning.dao.RoleDao;
import com.online_learning.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDao roleDao;

    public void initRoles() {
        List<Role> roles = roleDao.findAll();
        if (!roles.isEmpty())
            return;
        Role userRole = new Role("USER");
        Role groupActivitiesAccessRole = new Role("GROUP_ACTIVITIES_ACCESS");
        Role adminRole = new Role("ADMIN");
        roles.add(userRole);
        roles.add(groupActivitiesAccessRole);
        roles.add(adminRole);
        roleDao.saveAll(roles);
    }

}
