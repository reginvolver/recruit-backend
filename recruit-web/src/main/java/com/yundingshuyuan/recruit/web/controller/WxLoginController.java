package com.yundingshuyuan.recruit.web.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/wxlogin")
public class WxLoginController {
    @Autowired
    private WxMaService wxMaService;

    @GetMapping("/getOpenId")
    public BasicResultVO<String> login(String code) {
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            String openid = session.getOpenid();
            return BasicResultVO.success(openid);
        } catch (WxErrorException e) {
            return BasicResultVO.fail(e.getError().getErrorMsg());
        }
    }
}
