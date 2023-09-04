package com.yundingshuyuan.recruit.web.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.yundingshuyuan.recruit.api.UserInfoService;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;
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
@RequestMapping("/miniapp/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;


    @GetMapping("/show")
    @Operation(summary = "展示用户信息")
    public UserInfoVo show(@RequestParam("cloudId") String cloudId) {
        return userInfoService.showUserInfo(cloudId);
    }

    @PostMapping("/save")
    @Operation(summary = "保存用户信息")
    public BasicResultVO save(@RequestBody String json) {
        JSON parse = JSONUtil.parse(json);
        UserInfoVo userInfoVo = parse.toBean(UserInfoVo.class);
        if (userInfoService.saveUserInfo(userInfoVo)) {
            return BasicResultVO.success("保存成功");
        }
        return BasicResultVO.fail("保存失败");
    }
}
