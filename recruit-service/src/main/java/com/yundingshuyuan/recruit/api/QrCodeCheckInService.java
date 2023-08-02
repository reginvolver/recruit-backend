package com.yundingshuyuan.recruit.api;


/**
 * @author wys
 */
public interface QrCodeCheckInService {

    /**
     * 创建一个签到事件
     *
     * @param openId     数据
     * @param eventName  签到事件名称
     * @param expireTime 二维码有效期
     * @return QrCode的base64编码
     */
    String createQrCode(String openId, String eventName, int expireTime);

    /**
     * 解析二维码信息
     *
     * @param <T>      数据类型
     * @param scanInfo 前端返回的二维码中的信息
     * @return 签到事件信息
     */
    void parseQrCodeInfo(String scanInfo);

}