
package com.online_learning.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online_learning.dto.Init;
import com.online_learning.service.InitService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InitController {

    private final InitService initService;

    @PostMapping("/init")
    public Init init() throws Exception {
        initService.init();
        Init init = new Init();
        init.setMessage("Init success");
        init.setSuccess(true);
        return init;
    }
}