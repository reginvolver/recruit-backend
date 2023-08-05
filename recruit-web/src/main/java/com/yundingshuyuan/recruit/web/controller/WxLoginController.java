package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.service.IWxLoginService;
import com.yundingshuyuan.vo.BasicResultVO;
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
     * 若登录成功，封装的结果集有map对象
     * map里包括openid和session_key
     *
     * @param code
     * @return
     */
    @RequestMapping("/getWxMes")
    @ResponseBody
    public BasicResultVO getWxMes(String code) {
        return wxLoginService.getWxMes(code);
    }
}
