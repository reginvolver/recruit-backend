package com.yundingshuyuan.recruit.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yundingshuyuan.enums.RespStatusEnum;
import com.yundingshuyuan.enums.WxRespStatusEnum;
import com.yundingshuyuan.recruit.api.IWxLoginService;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
@RequiredArgsConstructor
public class WxLoginServiceImpl implements IWxLoginService {

    private final UserInfoMapper userInfoMapper;

    private final WxMaService wxMaService;

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return JSON.toJSONString(userInfo);
    }

/*    *//**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     *//*
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        if (!wxMaService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return JsonUtils.toJson(phoneNoInfo);
    }*/

    @Override
    public BasicResultVO<String> wxLogin(@RequestBody String code) {
        // 这里前端不能直接传code吗？
        if (StringUtils.isBlank(code)) {
            return new BasicResultVO<>(WxRespStatusEnum.ERROR_JSON_CODE);
        }
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            log.info("用户登录小程序" + session.getSessionKey(),session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return new BasicResultVO<>(WxRespStatusEnum.LOGIN_SUCCESS, session.getOpenid());
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return BasicResultVO.fail(e.getMessage());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }


    /**
     * 根据登录时的openid查找用户的id和权限
     *
     * @param openid
     * @return
     */

    public UserInfo getLoginMes(String openid) {
        try {
            return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("cloud_id", openid));
        } catch (Exception e) {
            // 存在多条数据
            log.error(WxRespStatusEnum.DUPLICATE_DATA_EXCEPTION.getMsg(), e.getMessage());
            return null;
        }
    }
}
