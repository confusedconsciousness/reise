package com._xkishan.journal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    // GET verb + /health-check -> URL
    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }
}
