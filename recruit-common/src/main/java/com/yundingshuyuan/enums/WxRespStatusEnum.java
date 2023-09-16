package com.yundingshuyuan.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WxRespStatusEnum implements RespStatus{
    /**
     * 成功响应状态
     */
    LOGIN_SUCCESS("B0200","二维码解析成功"),

    /**
     * 失败响应状态
     */
    ERROR_JSON_CODE("-1", "错误的JsonCode"),
    DUPLICATE_DATA_EXCEPTION("-999", "用户多条信息异常，请联系后端"),
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
        return "微信";
    }
}
