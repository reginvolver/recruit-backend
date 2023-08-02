package com.yundingshuyuan.recruit.service.handler;

import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;

/**
 * 签到处理类接口
 *
 * @param <T>
 */
public interface CheckInHandler<T> {

    /**
     * 获取处理事件名称
     *
     * @return handler 对应的 CheckInEvent事件名称
     */
    String getBindingName();

    /**
     * 签到操作 -> 根据 CheckInEvent事件信息
     *
     * @param event
     * @param mapper
     */
    void doCheckIn(CheckInEvent<?> event, QrCodeCheckInMapper mapper);

    /**
     * 通过 openId 获取二维码中要封装的额外信息
     *
     * @param openId
     * @param mapper
     * @return T 额外信息
     */
    T handleByOpenId(String openId, QrCodeCheckInMapper mapper);

    /**
     * 签到数据加密
     *
     * @param data       待加密的签到数据
     * @param createTime 创建时间戳
     * @param expireTime 有效时间戳
     * @return 加密结果
     */
    String encipher(T data, long createTime, long expireTime);

    /**
     * 签到数据解密
     *
     * @param saltAndEncryptedData 待解密数据
     * @return 扫码结果
     */
    CheckInEvent<T> decipher(String saltAndEncryptedData);

}