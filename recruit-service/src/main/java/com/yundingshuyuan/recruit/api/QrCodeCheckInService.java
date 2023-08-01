package com.yundingshuyuan.recruit.api;

/**
 * @author wys
 */
public interface QrCodeCheckInService {


    /**
     * 创建一个签到事件
     *
     * @param data      数据
     * @param eventName 签到事件名称
     * @param tClass    数据类型
     * @param <T>       数据类型
     * @return QrCode的base64编码
     */
    String createQrCode(String openId, String eventName);

    /**
     * 解析二维码信息
     *
     * @param scanInfo 前端返回的二维码中的信息
     * @param <T>      数据类型
     * @return 签到事件信息
     */
    void parseQrCodeInfo(String openId, String scanInfo);
}