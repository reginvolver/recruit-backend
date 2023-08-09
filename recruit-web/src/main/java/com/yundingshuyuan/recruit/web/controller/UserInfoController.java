package com.yundingshuyuan.recruit.web.controller;

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

    @PostMapping("/updateUserInfo")
    @Operation(summary = "修改用户信息")
    public BasicResultVO updateUser(@RequestBody UserInfoVO userInfoVO) {
        if (userInfoService.updateUserInfo(userInfoVO)) {
            return BasicResultVO.success("修改成功");
        }
        return BasicResultVO.fail("修改失败");
    }

    @GetMapping("/show")
    @Operation(summary = "展示用户信息")
    public UserInfoVO show(Integer cloudId) {
        return userInfoService.showUserInfo(cloudId);
    }

    @PostMapping("/save")
    @Operation(summary = "保存用户信息")
    public BasicResultVO save(@RequestBody UserInfoVO userInfoVO) {
        if (userInfoService.saveUserInfo(userInfoVO)) {
            return BasicResultVO.success("保存成功");
        }
        return BasicResultVO.fail("保存失败");
    }
}
