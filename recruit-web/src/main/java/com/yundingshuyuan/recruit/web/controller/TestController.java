package com.yundingshuyuan.recruit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "测试接口")
public class TestController {

    @Value("${test}")
    private String test;

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    String test() {
        return test;
    }

}
