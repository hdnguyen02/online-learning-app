
package com.online_learning.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InitService {

    private final UserService userService;
    private final RoleService roleService;
    private final LanguageService languageService;
    private final InvoiceService invoiceService;

    public void init() throws Exception {
        languageService.initLanguages();
        roleService.initRoles();
        userService.initUsers();
        invoiceService.initInvoice();
    }

}