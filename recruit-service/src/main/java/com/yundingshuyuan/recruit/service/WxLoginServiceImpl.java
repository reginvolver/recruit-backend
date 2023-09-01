package com.yundingshuyuan.recruit.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yundingshuyuan.recruit.api.IWxLoginService;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@Slf4j
public class WxLoginServiceImpl implements IWxLoginService {

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String secret;


    @Override
    public BasicResultVO<String> getOpenid(@RequestBody String data) {
        System.out.println(data);
        JSONObject dataObject = JSONUtil.parseObj(data);
        String code = dataObject.get("code", String.class);

        String authUrl = "https://api.weixin.qq.com/sns/jscode2session?&grant_type=authorization_code";
        authUrl = authUrl + "&appid=" + appid + "&secret=" + secret + "&js_code=" + code;
        String result = HttpUtil.get(authUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);

        if (StringUtils.contains(result, "errcode")) {
            return BasicResultVO.fail();
        }

        String openid = jsonObject.getStr("openid");

        return BasicResultVO.success(openid);

    }

}
