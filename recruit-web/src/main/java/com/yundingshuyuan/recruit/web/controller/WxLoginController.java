package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.service.IWxLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/wxlogin")
public class WxLoginController {

    @Autowired
    public IWxLoginService wxLoginService;

    /**
     * 若返回openid则登录成功
     * 若返回""则登录失败
     *
     * @param code
     * @return
     */
    @RequestMapping("/getOpenId")
    @ResponseBody
    public String getWxOpenId(String code) {
        String openId = wxLoginService.getOpenId(code);
        return openId;
    }
}
