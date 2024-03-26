package com.atwaha.sis.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @GetMapping
    public String get() {
        return "GET:: Admin Controller";
    }

    @PostMapping
    public String post() {
        return "POST:: Admin Controller";
    }

    @PatchMapping
    public String patch() {
        return "PATCH:: Admin Controller";
    }

    @DeleteMapping
    public String delete() {
        return "DELETE:: Admin Controller";
    }
}
