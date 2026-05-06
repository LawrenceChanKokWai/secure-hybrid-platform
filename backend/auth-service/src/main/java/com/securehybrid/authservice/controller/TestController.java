package com.securehybrid.authservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/api/secure")
    public Map<String, Object> secure(Authentication authentication) {
        return java.util.Map.of(
                "message", "This is secured",
                "user", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }
}
