package com.yundingshuyuan.recruit.service;

public interface IWxLoginService {

    /**
     * 用户登录时根据code返回openid
     *
     * @param code
     * @return
     */
    public String getOpenId(String code);
}
