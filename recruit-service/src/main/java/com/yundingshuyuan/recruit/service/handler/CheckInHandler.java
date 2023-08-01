package com.yundingshuyuan.recruit.service.handler;

import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;

public interface CheckInHandler<T> {

    String getName();

    /**
     * 签到操作
     *
     * @param data 对应的签到事件数据
     */
    void doCheckIn(CheckInEvent<T> event, QrCodeCheckInMapper mapper);

    /**
     * 通过 openId 获取二维码中要封装的额外信息
     *
     * @param openId
     * @return T 额外信息
     */
    T handleByOpenId(String openId, QrCodeCheckInMapper mapper);

    /**
     * 签到数据加密
     *
     * @param data 待加密的签到数据
     * @return 加密结果
     */
    String encipher(T data);

    /**
     * 签到数据解密
     *
     * @param data 待解密数据
     * @return 解密结果
     */
    CheckInEvent<T> decipher(String encryptedEvent);
}