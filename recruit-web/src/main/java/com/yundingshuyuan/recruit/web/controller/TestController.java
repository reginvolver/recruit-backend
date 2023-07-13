package com.yundingshuyuan.recruit.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${test}")
    private String test;

    @GetMapping("/test")
    String test() {
        return test;
    }
}
