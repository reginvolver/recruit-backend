package com.yundingshuyuan.recruit.web.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.yundingshuyuan.recruit.api.UserInfoService;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVO;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RecruitResult
@Tag(name = "用户接口")
@Slf4j
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;


    @GetMapping("/show")
    @Operation(summary = "展示用户信息")
    public UserInfoVO show(@RequestParam("cloudId") String cloudId) {
        log.info("{}", cloudId);
        Integer integer1 = Integer.valueOf(cloudId);
        return userInfoService.showUserInfo(integer1);
    }

    @PostMapping("/save")
    @Operation(summary = "保存用户信息")
    public BasicResultVO save(@RequestBody String json) {
        JSON parse = JSONUtil.parse(json);
        UserInfoVO userInfoVO = parse.toBean(UserInfoVO.class);
        if (userInfoService.saveUserInfo(userInfoVO)) {
            return BasicResultVO.success("保存成功");
        }
        return BasicResultVO.fail("保存失败");
    }
}
