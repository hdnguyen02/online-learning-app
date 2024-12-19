package com.online_learning.controller;

import com.online_learning.dto.Health;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class HealthController {

    @GetMapping("/health")
    public Health health() {
        Health health = new Health();
        health.setName_service("Online learning app");
        health.setVersion("v1");
        return health;
    }
}
