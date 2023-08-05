package com.yundingshuyuan.recruit.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.service.IWxLoginService;
import com.yundingshuyuan.vo.BasicResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxLoginServiceImpl implements IWxLoginService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String secret;

    /**
     * 用户登录时，根据前端传来的code，
     * 生成openid和session_key
     * 然后返回map集合
     *
     * @param code
     * @return
     */
    @Override
    public BasicResultVO getWxMes(String code) {
        String authUrl = "https://api.weixin.qq.com/sns/jscode2session?&grant_type=authorization_code";
        authUrl = authUrl + "&appid=" + appid + "&secret=" + secret + "&js_code=" + code;
        String result = HttpUtil.get(authUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);

        if (StringUtils.contains(result, "errcode")) {
            return BasicResultVO.fail();
        }

        String openid = jsonObject.getStr("openid");
        String sessionKey = jsonObject.getStr("session_key");

        Map<String, String> map = new HashMap<>();
        map.put("openid", openid);
        map.put("session_key", sessionKey);

        return BasicResultVO.success(map);

    }

}
