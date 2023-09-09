package com.yundingshuyuan.recruit.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yundingshuyuan.recruit.api.IWxLoginService;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxLoginServiceImpl implements IWxLoginService {

    @Value("wxf910237ed6466ca7")
    private String appid;

    @Value("9c37e0c18643739b08b32d72a3dc332a")
    private String secret;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public String getOpenid(@RequestBody String data) {
        System.out.println(data);
        JSONObject dataObject = JSONUtil.parseObj(data);
        String code = dataObject.get("code", String.class);

        String authUrl = "https://api.weixin.qq.com/sns/jscode2session?&grant_type=authorization_code";
        authUrl = authUrl + "&appid=" + appid + "&secret=" + secret + "&js_code=" + code;
        String result = HttpUtil.get(authUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);

        if (StringUtils.contains(result, "errcode")) {
            return "";
        }

        String openid = jsonObject.getStr("openid");

        return openid;
    }


    /**
     * 根据登录时的openid查找用户的id和权限
     * @param openid
     * @return
     */
    public Map<String,Integer> getLoginMes(String openid) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("cloud_id", openid);

        Integer id = null;
        Integer isAdmin = null;

        try {
            UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
            id = userInfo.getId();
            isAdmin = userInfo.getIsAdmin();
        } catch (Exception e) {
            id = null;
            isAdmin = null;
        } finally {
            Map<java.lang.String, java.lang.Integer> map = new HashMap<>();
            map.put("id", id);
            map.put("isAdmin", isAdmin);
            return map;
        }
    }
}
