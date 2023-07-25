package com.yundingshuyuan.recruit.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.yundingshuyuan.recruit.api.LoginService;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RecruitResult
@Tag(name = "登录接口")
@Slf4j
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    @SaCheckLogin
    @Operation(summary = "登录接口")
    public BasicResultVO login(String username, String password){
        log.info("username:{},password:{}",username,password);
        loginService.login(username, password);
        log.info("用户身份：{}", StpUtil.getRoleList());
        log.info("用户权限：{}",StpUtil.getPermissionList());
        String result = "用户身份：" + StpUtil.getRoleList() + "用户权限：" + StpUtil.getPermissionList();
        if(loginService.isLogin()){
            return BasicResultVO.success("登录成功", result);
        }
        return BasicResultVO.fail("登录失败");
    }
}
