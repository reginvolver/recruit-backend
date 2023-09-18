package com.yundingshuyuan.recruit.web.controller;

import com.alibaba.fastjson.JSON;
import com.yundingshuyuan.recruit.api.IWxLoginService;
import com.yundingshuyuan.recruit.domain.LoginMes;
import com.yundingshuyuan.recruit.domain.User;
import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.recruit.web.exception.CommonException;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RecruitResult
@RestController
@Tag(name = "小程序登录接口")
@Slf4j
@RequestMapping("/miniapp/wxlogin")
public class WxLoginController {

    @Autowired
    public IWxLoginService wxLoginService;

    @Operation(summary = "获取用户登录时的openid,userid,权限")
    @PostMapping("/getMessage")
    public BasicResultVO<String> getMessage(@RequestParam String code) {
        // 登录
        try {
            return wxLoginService.wxLogin(code);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
