package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.IWxLoginService;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/wxlogin")
public class WxLoginController {
    @Autowired
    public IWxLoginService wxLoginService;

    @PostMapping("/getOpenid")
    @ResponseBody
    public BasicResultVO<String> getOpenid(@RequestBody String data) {
        BasicResultVO result = wxLoginService.getOpenid(data);
        return result;
    }
}
