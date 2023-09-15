package com.yundingshuyuan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum TicketGrabRespStatusEnum implements RespStatus{
    /**
     * 成功响应状态
     *
     */
    GET_TICKET("-1", "恭喜！同学抢到了 第{}场宣讲会 的门票"),
    CHECKIN_SUCCESS("B0200","二维码解析成功"),

    /**
     * 失败响应状态
     */
    FREQUENT_REQUEST("-1", "请不要频繁请求"),
    NOT_GET_TICKET("-1", "没有抢到票！( º﹃º )"),
    CHECKIN_HANDLER_NOT_EXIST("-1","找不到对应的签到事件处理类"),
    TICKETS_ALREADY_AVAILABLE("-1","已经抢到票了！"),
    NO_TICKET_SURPLUS("-1","票抢完了(〒︿〒)"),
    TIME_NOT_ALLOW("B0000","未到抢票时间");

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
        return "秒杀抢票";
    }

}
