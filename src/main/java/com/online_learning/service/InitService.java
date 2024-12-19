
package com.online_learning.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InitService {

    private final UserService userService;
    private final RoleService roleService;
    private final LanguageService languageService;

    public void init() {
        languageService.initLanguages();
        roleService.initRoles();
        userService.initUsers();
    }

}