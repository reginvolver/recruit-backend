package com.yundingshuyuan.recruit.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzt.logapi.starter.annotation.LogRecord;
import com.yundingshuyuan.enums.RespStatusEnum;
import com.yundingshuyuan.recruit.api.TestService;
import com.yundingshuyuan.recruit.domain.User;
import com.yundingshuyuan.recruit.utils.RedisUtils;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Autowired
    private TestService testService;

    @Value("${test}")
    private String test;

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    @LogRecord(
            success = "张三成功完成",
            type = "User", bizNo = "1")
    public User test() {
        boolean redisTest = redisUtils.set("test", test);
        log.info("redisTest:{}", redisTest);
        return new User(Long.valueOf(test));
    }



    @GetMapping("/test2")
    @Operation(summary = "测试接口2")
    public Page<User> test2() {
        return testService.test();
    }
}