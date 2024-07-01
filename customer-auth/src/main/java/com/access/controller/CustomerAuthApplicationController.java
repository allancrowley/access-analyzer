package com.access.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerAuthApplicationController {

    @PostMapping("/test")
    public String test() {
        return "test";
    }
}
