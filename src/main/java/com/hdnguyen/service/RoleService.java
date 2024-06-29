package com.hdnguyen.service;


import com.hdnguyen.dao.RoleDao;
import com.hdnguyen.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDao roleDao;

    public void initRoles() {
        List<Role> roles = roleDao.findAll();
        if (!roles.isEmpty()) return;
        Role roleStudent = new Role("STUDENT");
        Role roleTeacher = new Role("TEACHER");
        Role roleAdmin = new Role("ADMIN");
        roles.add(roleStudent);
        roles.add(roleTeacher);
        roles.add(roleAdmin);
        roleDao.saveAll(roles);
    }

}
