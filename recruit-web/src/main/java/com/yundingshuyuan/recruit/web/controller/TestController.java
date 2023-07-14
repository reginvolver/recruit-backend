package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.utils.RedisUtils;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "测试接口")
@RecruitResult
@Slf4j
public class TestController {

    @Autowired
    private RedisUtils redisUtils;

    @Value("${test}")
    private String test;

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public User test() {
        boolean redisTest = redisUtils.set("test", test);
        log.info("redisTest:{}", redisTest);
        return new User("张三", test);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class User {
        private String name;
        private String age;
    }
}
