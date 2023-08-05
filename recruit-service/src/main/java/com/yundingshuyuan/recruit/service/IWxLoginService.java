package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.vo.BasicResultVO;

public interface IWxLoginService {

    /**
     * 用户登录时，根据前端传来的code，
     * 生成openid和session_key
     * 然后返回map集合
     *
     * @param code
     * @return
     */
    public BasicResultVO getWxMes(String code);
}
