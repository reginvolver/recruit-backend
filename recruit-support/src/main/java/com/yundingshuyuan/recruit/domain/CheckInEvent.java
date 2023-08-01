package com.yundingshuyuan.recruit.domain;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 基础签到事件
 *
 * @author wys
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInEvent<T> {
    /**
     * 签到管理员 (不存)
     */
    private String user;
    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;
    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;
    /**
     * 签到时间 (不存)
     */
    private LocalDateTime signInTime;
    /**
     * 签到地点 (不存)
     */
    private String location;
    /**
     * 其他信息参数
     */
    private T data;

    /**
     * 从字符串中解析出签到信息
     *
     * @param str
     * @return 基础签到事件对象
     */
    public static <T> CheckInEvent<T> parseString(String str) {
        return JSONUtil.toBean(str, new TypeReference<CheckInEvent<T>>() {
        }, true);
    }

    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

}
