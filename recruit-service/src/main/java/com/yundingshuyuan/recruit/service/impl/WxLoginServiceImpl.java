package com.yundingshuyuan.recruit.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.recruit.service.IWxLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WxLoginServiceImpl implements IWxLoginService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String secret;

    /**
     * 用户登录时根据code返回openid
     *
     * @param code
     * @return
     */
    @Override
    public String getOpenId(String code) {
        String authUrl = "https://api.weixin.qq.com/sns/jscode2session?&grant_type=authorization_code";
        authUrl = authUrl + "&appid=" + appid + "&secret=" + secret + "&js_code" + code;
        String result = HttpUtil.get(authUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String openid = jsonObject.getStr("openid");

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cloud_id", openid);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (userInfo != null) {
            return "";
        }

        if (openid == null) {
            return "";
        }

        return openid;
    }

}
