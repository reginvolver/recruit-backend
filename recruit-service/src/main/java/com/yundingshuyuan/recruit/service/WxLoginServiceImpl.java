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

    @Value("wx0df8511230c13054")
    private String appid;

    @Value("64e882d75203e6c45678c02a37a84ada")
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
