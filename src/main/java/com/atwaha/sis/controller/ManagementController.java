package com.atwaha.sis.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/management")
public class ManagementController {
    @GetMapping
    public String get() {
        return "GET:: Management Controller";
    }

    @PostMapping
    public String post() {
        return "POST:: Management Controller";
    }

    @PatchMapping
    public String patch() {
        return "PATCH:: Management Controller";
    }

    @DeleteMapping
    public String delete() {
        return "DELETE:: Management Controller";
    }
}
