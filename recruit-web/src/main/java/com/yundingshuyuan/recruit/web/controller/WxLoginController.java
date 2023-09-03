package com.yundingshuyuan.recruit.web.controller;


import com.yundingshuyuan.recruit.api.IWxLoginService;
import com.yundingshuyuan.recruit.domain.LoginMes;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "小程序登录接口")
@Slf4j
@RequestMapping("/miniapp/wxlogin")
public class WxLoginController {

    @Autowired
    public IWxLoginService wxLoginService;

    @Operation(summary = "获取用户登录时的openid,userid,权限")
    @PostMapping("/getMessage")
    @ResponseBody
    public BasicResultVO<LoginMes> getMessage(@RequestBody String data) {
        String openid = wxLoginService.getOpenid(data);
        Map<String, Integer> mes = wxLoginService.getLoginMes(openid);
        Integer id = mes.get("id");
        Integer isAdmin = mes.get("isAdmin");

        LoginMes loginMes = new LoginMes(openid,id,isAdmin);

        return BasicResultVO.success(loginMes);
    }

}
