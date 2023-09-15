package com.yundingshuyuan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
@AllArgsConstructor
public enum CheckInRespStatusEnum implements RespStatus{

    /**
     * 成功响应状态
     */
    CHECKIN_SUCCESS("B0200","二维码解析成功"),

    /**
     * 失败响应状态
     */
    ENCIPHER_FAIL("-1", "二维码数据解析失败"),
    OPENID_NOT_FOUND("-1", "openId不存在或者有误"),
    CHECKIN_HANDLER_NOT_EXIST("-1","找不到对应的签到事件处理类"),
    QRCODE_EXPIRED("-1","二维码超时失效"),
    CHECKIN_RECORD_EXIST("B0500","签到记录已经存在"),



    ;
    /**
     * 响应状态
     */
    private final String code;
    /**
     * 响应编码
     */
    private final String msg;

    @Override
    public String getDescription() {
        return "签到事件";
    }
}